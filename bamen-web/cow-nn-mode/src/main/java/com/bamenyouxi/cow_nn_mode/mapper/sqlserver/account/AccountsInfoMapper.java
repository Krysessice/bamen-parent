package com.bamenyouxi.cow_nn_mode.mapper.sqlserver.account;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.SysAgent;
import com.bamenyouxi.cow_nn_mode.model.sqlserver.account.AccountsInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


/**
 * mapper for QPAccountsDB.dbo.AccountsInfo
 * Created by hc on 2018/5/10.
 */
@Mapper
public interface AccountsInfoMapper extends CrudMapper<AccountsInfo, Integer> {

    List<AccountsInfo> getList(Map<String ,Object> params);

}
