package com.bamenyouxi.room_card_mode.mapper.sqlserver.account;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.room_card_mode.model.sqlserver.account.AccountsInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * mapper for QPAccountsDB.dbo.AccountsInfo
 * Created by hc on 2017/7/9.
 */
@Mapper
public interface AccountsInfoMapper extends CrudMapper<AccountsInfo, Integer> {

    List<AccountsInfo> getUser(String F_ACCOUNT);
}
