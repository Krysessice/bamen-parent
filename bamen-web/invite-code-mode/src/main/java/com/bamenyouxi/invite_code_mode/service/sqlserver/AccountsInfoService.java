package com.bamenyouxi.invite_code_mode.service.sqlserver;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.account.AccountsInfoMapper;
import com.bamenyouxi.invite_code_mode.model.sqlserver.account.AccountsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户信息service
 * Created by 13477 on 2017/8/9.
 */
@Service
public class AccountsInfoService extends AbstractCrudService<AccountsInfo, Integer> {
	@Autowired
	private AccountsInfoMapper accountsInfoMapper;

	@Override
	public CrudMapper<AccountsInfo, Integer> getMapper() {
		return accountsInfoMapper;
	}
}
