package com.bamenyouxi.cow_nn_mode.service.sqlserver.account;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.cow_nn_mode.mapper.sqlserver.account.AccountsInfoMapper;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.SysAgent;
import com.bamenyouxi.cow_nn_mode.model.sqlserver.account.AccountsInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
	public PageInfo<AccountsInfo> getList(int page, int size, Map<String,Object> params){
		List<AccountsInfo> list=accountsInfoMapper.getList(params);
		return queryAllLists(page,size,list);
	}

	@Transactional
	private PageInfo<AccountsInfo> queryAllLists(int pageNum, int pageSize, List<AccountsInfo> accountsInfos) {
		//分页之后的对象
		List<AccountsInfo> sysAgentVoNews = new ArrayList<>();

		int currIdx = (pageNum > 1 ? (pageNum - 1) * pageSize : 0);
		for (int i = 0; i < pageSize && i < accountsInfos.size() - currIdx; i++) {
			AccountsInfo appGame = accountsInfos.get(currIdx + i);
			sysAgentVoNews.add(appGame);
		}

		PageInfo<AccountsInfo> page = new PageInfo<>(sysAgentVoNews);
		page.setSize(accountsInfos.size());
		page.setPageNum(pageNum);
		page.setPageSize(pageSize);
		page.setTotal(accountsInfos.size());
		page.setPages(accountsInfos.size() % pageSize == 0 ? accountsInfos.size() / pageSize
				: accountsInfos.size() / pageSize + 1);

		page.setIsFirstPage(pageNum == 1 ? true : false);
		page.setIsLastPage(pageNum == page.getPages() ? true : false);
		page.setHasPreviousPage(pageNum - 1 > 0 ? true : false);
		page.setHasNextPage(pageNum < page.getPages() ? true : false);
		page.setFirstPage(sysAgentVoNews.size() == 0 ? 0 : 1);
		page.setPrePage(page.isHasPreviousPage() ? pageNum - 1 : 0);
		page.setNextPage(page.isHasNextPage() ? pageNum + 1 : 0);
		page.setLastPage(page.getPages());
		page.setStartRow(currIdx);
		page.setEndRow(accountsInfos.size() - currIdx);
		return page;
	}




}
