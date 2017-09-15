package com.bamenyouxi.invite_code_mode.web.web_api.sqlserver;

import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.impl.web.AbstractCrudController;
import com.bamenyouxi.invite_code_mode.model.sqlserver.account.GroupRoomMember;
import com.bamenyouxi.invite_code_mode.service.sqlserver.GroupRoomMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 群开房用户列表controller
 * Created by 13477 on 2017/8/31.
 */
@RestController
@RequestMapping("/groupRoomMember")
final class GroupRoomMemberController extends AbstractCrudController<GroupRoomMember, Long> {
	@Autowired
	private GroupRoomMemberService groupRoomMemberService;

	@Override
	public AbstractCrudService<GroupRoomMember, Long> getService() {
		return groupRoomMemberService;
	}
}
