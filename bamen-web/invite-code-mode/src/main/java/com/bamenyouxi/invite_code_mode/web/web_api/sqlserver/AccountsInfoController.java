package com.bamenyouxi.invite_code_mode.web.web_api.sqlserver;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.impl.web.AbstractCrudController;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.invite_code_mode.model.sqlserver.account.AccountsInfo;
import com.bamenyouxi.invite_code_mode.service.sqlserver.AccountsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * 用户信息 controller
 * Created by 13477 on 2017/8/9.
 */
@RestController
@RequestMapping("/account")
final class AccountsInfoController extends AbstractCrudController<AccountsInfo, Integer> {
	@Autowired
	private AccountsInfoService accountsInfoService;

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
}
