package com.bamenyouxi.invite_code_mode.service.sqlserver;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.account.AccountsInfoMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SysAgent;
import com.bamenyouxi.invite_code_mode.model.sqlserver.account.AccountsInfo;
import com.bamenyouxi.invite_code_mode.model.sqlserver.account.GroupRoom;
import com.bamenyouxi.invite_code_mode.util.UserDetailsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

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


	@Transactional
	public PageInfo<AccountsInfo> queryLook(int page, int size, Map<String, Object> params){
		super.listBefore(params);
		PageHelper.startPage(page,size,FieldConstant.SortConstant.LOOK_CARD);
		List<AccountsInfo> list=accountsInfoMapper.queryLook(params);
		if (list.isEmpty() || list.get(0) == null)
			return new PageInfo<>();
		return new PageInfo<>(list);
	}

	@Transactional
	public PageInfo<SysAgent> getActive(int page, int size, Map<String, Object> params){
		List<SysAgent> list=accountsInfoMapper.getActive(params);
		if (list.isEmpty() || list.get(0) == null)
			return new PageInfo<>();
		return new PageInfo<>(list);
	}

}
