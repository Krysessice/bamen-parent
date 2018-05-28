package com.bamenyouxi.invite_code_mode.web.web_api.sqlserver;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.impl.web.AbstractCrudController;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.account.GroupRoomMapper;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.account.GroupRoomMemberMapper;
import com.bamenyouxi.invite_code_mode.model.sqlserver.account.GroupRoom;
import com.bamenyouxi.invite_code_mode.model.sqlserver.account.GroupRoomMember;
import com.bamenyouxi.invite_code_mode.service.sqlserver.GroupRoomService;
import com.bamenyouxi.invite_code_mode.util.UserDetailsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

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

	@Autowired
	private GroupRoomMapper groupRoomMapper;
	@Autowired
	private GroupRoomMemberMapper groupRoomMemberMapper;

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


	@DeleteMapping("/deleteRoom/{id}")
	public WebResult deleteRoom(@PathVariable Integer id){
		Assert.notNull(id, TipMsgConstant.PARAM_INVALID);
		GroupRoom groupRooms=groupRoomMapper.getStatus(id);
		if(groupRooms.getQunStatus()!=2){
			int n=0;
			n=groupRoomMapper.delete(GroupRoom.of().id(id));
			n=groupRoomMemberMapper.delete(GroupRoomMember.of().groupRoomId(id));
		}else{
			return WebResult.of(0,TipMsgConstant.SYS_DELETEROOM,null);
		}
		return WebResult.of();
	}

	@GetMapping("/queryCountRoom/")
	private WebResult queryCountRoom(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size
			) {
		return WebResult.of(groupRoomService.queryCountRoom(page,size));
	}


	@PostMapping("/saveParams/")
	private WebResult saveParams(@RequestBody GroupRoom groupRoom){
		 groupRoomService.saveParams(groupRoom);
		 return WebResult.of();
	}



}
