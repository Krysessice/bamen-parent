package com.bamenyouxi.room_card_mode.mapper.sqlserver.account;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.room_card_mode.model.sqlserver.account.AccountsInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


/**
 * mapper for QPAccountsDB.dbo.AccountsInfo
 * Created by hc on 2017/7/9.
 */
@Mapper
public interface AccountsInfoMapper extends CrudMapper<AccountsInfo, Integer> {

    List<AccountsInfo> getUser(String F_ACCOUNT);

    List<AccountsInfo> lookCard(Integer gameId);

    int updateLook(int gameId);

    List<AccountsInfo> queryLook(Map<String,Object> params);

    int updateAgentLookCard(int gameId);

    List<AccountsInfo> giveAgent(Integer gameId);

    int updateGiveAgent(int gameId);

    List<AccountsInfo> queryGiveAgent(Map<String,Object> params);

    int authorizeGiveAgent(int gameId);

}
