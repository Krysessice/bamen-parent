package com.bamenyouxi.invite_code_mode.web.web_api.sqlserver;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.impl.web.AbstractCrudController;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.account.AccountsInfoMapper;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.account.GroupRoomMapper;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.account.GroupRoomMemberMapper;
import com.bamenyouxi.invite_code_mode.model.sqlserver.account.AccountsInfo;
import com.bamenyouxi.invite_code_mode.model.sqlserver.account.GroupRoom;
import com.bamenyouxi.invite_code_mode.model.sqlserver.account.GroupRoomMember;
import com.bamenyouxi.invite_code_mode.service.sqlserver.GroupRoomMemberService;
import com.bamenyouxi.invite_code_mode.util.RedisUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * 群开房用户列表controller
 * Created by 13477 on 2017/8/31.
 */
@RestController
@RequestMapping("/groupRoomMember")
final class GroupRoomMemberController extends AbstractCrudController<GroupRoomMember, Long> {
	@Autowired
	private GroupRoomMemberService groupRoomMemberService;

	@Autowired
	private AccountsInfoMapper accountsInfoMapper;
	@Autowired
	private GroupRoomMapper groupRoomMapper;

	@Autowired
	private GroupRoomMemberMapper groupRoomMemberMapper;
	@Autowired
	private RedisUtil redisUtil;


	@Override
	public AbstractCrudService<GroupRoomMember, Long> getService() {
		return groupRoomMemberService;
	}


}
