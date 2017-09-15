package com.bamenyouxi.invite_code_mode.web.web_api.sqlserver;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.impl.web.AbstractCrudController;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.invite_code_mode.model.sqlserver.account.GroupRoom;
import com.bamenyouxi.invite_code_mode.service.sqlserver.GroupRoomService;
import com.bamenyouxi.invite_code_mode.util.UserDetailsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 群开房controller
 * Created by 13477 on 2017/8/31.
 */
@RestController
@RequestMapping("/groupRoom")
final class GroupRoomController extends AbstractCrudController<GroupRoom, Integer> {
	@Autowired
	private GroupRoomService groupRoomService;

	@Override
	public AbstractCrudService<GroupRoom, Integer> getService() {
		return groupRoomService;
	}

	@GetMapping("/listSelf/")
	private WebResult listSelf(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
			@RequestParam Map<String, Object> params) {
		params.put(FieldConstant.DBFieldConstant.Gameid.name(), UserDetailsUtil.getGameId());
		return WebResult.of(getService().list(page, size, params));
	}
}
