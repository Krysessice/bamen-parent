package com.bamenyouxi.invite_code_mode.service.sqlserver;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.invite_code_mode.constant.SysConstant;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.account.GroupRoomMapper;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.account.GroupRoomMemberMapper;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.record.RecordBuildQunRecordMapper;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.treasure.AgentQunRoomInforMapper;
import com.bamenyouxi.invite_code_mode.model.sqlserver.account.AccountsInfo;
import com.bamenyouxi.invite_code_mode.model.sqlserver.account.GroupRoom;
import com.bamenyouxi.invite_code_mode.model.sqlserver.account.GroupRoomMember;
import com.bamenyouxi.invite_code_mode.model.sqlserver.record.RecordBuildQunRecord;
import com.bamenyouxi.invite_code_mode.model.sqlserver.treasure.AgentQunRoomInfor;
import com.bamenyouxi.invite_code_mode.util.UserDetailsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 群开房service
 * Created by 13477 on 2017/8/31.
 */
@Service
public class GroupRoomService extends AbstractCrudService<GroupRoom, Integer> {
	@Autowired
	private GroupRoomMapper groupRoomMapper;
	@Autowired
	private GroupRoomMemberMapper groupRoomMemberMapper;

	@Autowired
	private AgentQunRoomInforMapper agentQunRoomInforMapper;

	@Autowired
	private RecordBuildQunRecordMapper recordBuildQunRecordMapper;

	@Override
	public CrudMapper<GroupRoom, Integer> getMapper() {
		return groupRoomMapper;
	}

	@Override
	protected String sort() {
		return FieldConstant.SortConstant.BUILD_DATE_DESC;
	}

	@Override
	protected void saveBefore(GroupRoom groupRoom) {
		Assert.notNull(groupRoom.getGroupName(), TipMsgConstant.PARAM_INVALID);
		int length = groupRoom.getGroupName().length();
		Assert.isTrue(length >= (int) SysConstant.EnumValue2.GROUP_NAME_LENGTH.getValue1(), TipMsgConstant.PARAM_INVALID);
		Assert.isTrue(length <= (int) SysConstant.EnumValue2.GROUP_NAME_LENGTH.getValue2(), TipMsgConstant.PARAM_INVALID);
		groupRoom
				.gameId(UserDetailsUtil.getGameId())
				.nickName(UserDetailsUtil.getNickName())
				.roomStatus(SysConstant.SqlserverFlagConstant.SUCCESS)
				.playerNumInit();
		List<GroupRoom> list = getMapper().get(new HashMap<String, Object>() {{
			put(FieldConstant.DBFieldConstant.Gameid.name(), groupRoom.getGameId());
			put(FieldConstant.DBFieldConstant.qunName.name(), groupRoom.getGroupName());
		}});
		Assert.isTrue(list.isEmpty(), TipMsgConstant.GROUP_NAME_EXIST);
		GroupRoom gr=groupRoomMapper.getRoom(UserDetailsUtil.getGameId());
		Assert.isTrue(gr.getGameId()<5,TipMsgConstant.PLAYER_IN_GROUP_UPPER_LIMIT);
		super.saveBefore(groupRoom);
	}


	@Transactional
	public PageInfo<GroupRoom> queryCountRoom(int page, int size){
		List<GroupRoom> list=groupRoomMapper.queryCountRoom(UserDetailsUtil.getGameId());
		if (list.isEmpty() || list.get(0) == null)
			return new PageInfo<>();
		return new PageInfo<>(list);
	}


	public void saveParams(GroupRoom groupRoom){
		Assert.notNull(groupRoom.getGroupName(), TipMsgConstant.PARAM_INVALID);
		int length = groupRoom.getGroupName().length();
		Assert.isTrue(length >= (int) SysConstant.EnumValue2.GROUP_NAME_LENGTH.getValue1(), TipMsgConstant.PARAM_INVALID);
		Assert.isTrue(length <= (int) SysConstant.EnumValue2.GROUP_NAME_LENGTH.getValue2(), TipMsgConstant.PARAM_INVALID);
		groupRoom
				.gameId(UserDetailsUtil.getGameId())
				.nickName(UserDetailsUtil.getNickName())
				.roomStatus(SysConstant.SqlserverFlagConstant.SUCCESS)
				.playerNumInit();
		List<GroupRoom> list = getMapper().get(new HashMap<String, Object>() {{
			put(FieldConstant.DBFieldConstant.Gameid.name(), groupRoom.getGameId());
			put(FieldConstant.DBFieldConstant.qunName.name(), groupRoom.getGroupName());
		}});
		Assert.isTrue(list.isEmpty(), TipMsgConstant.GROUP_NAME_EXIST);
		GroupRoom gr=groupRoomMapper.getRoom(UserDetailsUtil.getGameId());
		Assert.isTrue(gr.getGameId()<5,TipMsgConstant.PLAYER_IN_GROUP_UPPER_LIMIT);
		int i=groupRoomMapper.save(groupRoom);
		RecordBuildQunRecord recordBuildQunRecord=new RecordBuildQunRecord();
		recordBuildQunRecord.setQunid(groupRoom.getId());
		recordBuildQunRecord.setGameid(groupRoom.getGameId());
		recordBuildQunRecord.setQunName(groupRoom.getGroupName());
		recordBuildQunRecord.setNickName(groupRoom.getNickName());
		recordBuildQunRecord.setBuilDate(groupRoom.getCreateTime());
		int n=recordBuildQunRecordMapper.save(recordBuildQunRecord);
	}





}
