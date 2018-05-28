package com.bamenyouxi.cow_nn_mode.web.web_api.sqlserver;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.impl.web.AbstractCrudController;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.cow_nn_mode.mapper.sqlserver.account.AccountsInfoMapper;
import com.bamenyouxi.cow_nn_mode.model.sqlserver.account.AccountsInfo;
import com.bamenyouxi.cow_nn_mode.service.sqlserver.account.AccountsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
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

	@GetMapping("/queryGiveAgent/")
	public WebResult queryGiveAgent(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
			@RequestParam Map<String ,Object> params){
		return WebResult.of(accountsInfoService.getList(page,size,params));
	}



}
