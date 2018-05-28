package com.bamenyouxi.invite_code_mode.service.sqlserver;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.account.AccountsInfoMapper;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.account.AgentPartnerMapper;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.account.GroupRoomMapper;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.account.GroupRoomMemberMapper;
import com.bamenyouxi.invite_code_mode.model.sqlserver.account.AccountsInfo;
import com.bamenyouxi.invite_code_mode.model.sqlserver.account.GroupRoom;
import com.bamenyouxi.invite_code_mode.model.sqlserver.account.GroupRoomMember;
import com.bamenyouxi.invite_code_mode.util.RedisUtil;
import com.bamenyouxi.invite_code_mode.util.UserDetailsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;

/**
 * 群房间成员service
 * Created by 13477 on 2017/8/31.
 */
@Service
public class GroupRoomMemberService extends AbstractCrudService<GroupRoomMember, Long> {
	@Autowired
	private GroupRoomMemberMapper groupRoomMemberMapper;
	@Autowired
	private GroupRoomMapper groupRoomMapper;
	@Autowired
	private AccountsInfoMapper accountsInfoMapper;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private AgentPartnerMapper agentPartnerMapper;

	@Override
	public CrudMapper<GroupRoomMember, Long> getMapper() {
		return groupRoomMemberMapper;
	}

	@Override
	protected String sort() {
		return FieldConstant.SortConstant.AGENTROOMID_ASC;
	}

	@Override
	protected void saveBefore(GroupRoomMember groupRoomMember) {
		Assert.notNull(groupRoomMember, TipMsgConstant.PARAM_INVALID);
		if (groupRoomMember.getUserId() == null && groupRoomMember.getGameId() != null) {
			List<AccountsInfo> list = accountsInfoMapper.get(new HashMap<String, Object>() {{
				put(FieldConstant.DBFieldConstant.GameID.name(), groupRoomMember.getGameId());
			}});
			Assert.notEmpty(list, TipMsgConstant.PLAYER_NOT_EXIST);
			groupRoomMember.userId(list.get(0).getUserId());
		}
		Assert.isTrue(getMapper().get(new HashMap<String, Object>() {{
			put(FieldConstant.DBFieldConstant.AgentRoomid.name(), groupRoomMember.getGroupRoomId());
			put(FieldConstant.DBFieldConstant.Userid.name(), groupRoomMember.getUserId());
		}}).isEmpty(), TipMsgConstant.PLAYER_ALREADY_EXIST);
//		Assert.isTrue(PageHelper.count(() ->
//				getMapper().get(new HashMap<String, Object>() {{
//					put(FieldConstant.DBFieldConstant.Userid.name(), groupRoomMember.getUserId());
//				}})
//		) < redisUtil.getSystemInfo().getInGroupLimit(), TipMsgConstant.PLAYER_IN_GROUP_UPPER_LIMIT);
		super.saveBefore(groupRoomMember);
	}

	@Transactional
	@Override
	public GroupRoomMember save(GroupRoomMember groupRoomMember) {
		super.save(groupRoomMember);
		int i = groupRoomMapper.update(GroupRoom.of().id(groupRoomMember.getGroupRoomId()).playerNumInt(SysConstant.SysFlagConstant.ENABLE));
		Assert.isTrue(i > 0, TipMsgConstant.OPERATION_FAILED);
		return groupRoomMember;
	}

	@Transactional
	@Override
	public void delete(GroupRoomMember groupRoomMember) {
		super.delete(groupRoomMember);
		int n=agentPartnerMapper.deletePartnerGameid(groupRoomMember.getGameId());
		int i = groupRoomMapper.update(GroupRoom.of().id(groupRoomMember.getGroupRoomId()).playerNumInt(SysConstant.SysFlagConstant.MINUS));
		Assert.isTrue(i > 0, TipMsgConstant.OPERATION_FAILED);
	}





}
