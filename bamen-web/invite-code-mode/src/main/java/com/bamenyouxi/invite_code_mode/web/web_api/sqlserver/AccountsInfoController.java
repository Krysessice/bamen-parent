package com.bamenyouxi.invite_code_mode.web.web_api.sqlserver;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.impl.web.AbstractCrudController;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.account.AccountsInfoMapper;
import com.bamenyouxi.invite_code_mode.model.sqlserver.account.AccountsInfo;
import com.bamenyouxi.invite_code_mode.model.sqlserver.account.GroupRoomMember;
import com.bamenyouxi.invite_code_mode.service.sqlserver.AccountsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户信息 controller
 * Created by 13477 on 2017/8/9.
 */
@RestController
@RequestMapping("/account")
final class AccountsInfoController extends AbstractCrudController<AccountsInfo, Integer> {
	@Autowired
	private AccountsInfoService accountsInfoService;

	@Autowired
	private AccountsInfoMapper accountsInfoMapper;

	@Override
	public AbstractCrudService<AccountsInfo, Integer> getService() {
		return accountsInfoService;
	}

	@GetMapping("/withCardNum")
	private WebResult getWithCardNum(Integer gameId) {
		return WebResult.of(accountsInfoService.get(new HashMap<String, Object>() {{
			put(FieldConstant.DBFieldConstant.GameID.name(), gameId);
			put(FieldConstant.CommonFieldConstant.withCardNum.name(), SysConstant.SysFlagConstant.ENABLE);
		}}));
	}


	@GetMapping("/lookCard/{gameId}")
	public WebResult lookCard(@PathVariable Integer gameId){
		List<AccountsInfo> list=accountsInfoMapper.lookCard(gameId);
		if(list!=null&&!list.isEmpty()){
			boolean s=list.get(0).getCustomID()==1;
			if(s){
				Assert.isTrue(true,TipMsgConstant.AGENT_LOOK_CARD);
				return  WebResult.of();
			}
			int n=accountsInfoMapper.updateLook(gameId);
			Assert.isTrue(n > 0, TipMsgConstant.OPERATION_FAILED);
			return WebResult.of(list.get(0));
		}
		Assert.isTrue(list.size()<0, TipMsgConstant.SYS_PLAY);
		return WebResult.of();
	}

	@GetMapping("/queryLook/")
	public WebResult queryLook(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
			@RequestParam Map<String, Object> params){
		return WebResult.of(accountsInfoService.queryLook(page,size,params));
	}

	@PutMapping("/updateAgentLookCard/{gameId}")
	public WebResult updateAgentLookCard(@PathVariable int gameId){
		int n=accountsInfoMapper.updateAgentLookCard(gameId);
		Assert.isTrue(n > 0, TipMsgConstant.OPERATION_FAILED);
		return WebResult.of();
	}

	@GetMapping("/cardactiveUser/")
	private WebResult getActive(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
			@RequestParam Map<String, Object> params) {
		return WebResult.of(accountsInfoService.getActive(page, size, params));
	}

}
