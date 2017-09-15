package com.bamenyouxi.invite_code_mode.mapper.sqlserver.account;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SysAgent;
import com.bamenyouxi.invite_code_mode.model.sqlserver.account.AccountsInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * mapper for QPAccountsDB.dbo.AccountsInfo
 * Created by hc on 2017/7/9.
 */
@Mapper
public interface AccountsInfoMapper extends CrudMapper<AccountsInfo, Integer> {

	List<SysAgent> getOfSysAgent(Map<String, Object> params);

	List<SysAgent> getBySuperAgentTimeBetweenOfSysAgent(Map<String, Object> params);

	@Select("select " +
			"   GameID gameId, PlayingGame superAgentGameId, SuperAgentTime transMemberTime LastLogonDate" +
			"from AccountsInfo " +
			"where SuperAgentTime is not null")
	List<SysAgent> findBySuperAgentTimeNotNullOfSysAgent();
}
