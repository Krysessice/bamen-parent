package com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SysAgent;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SysAgentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * mapper for table: t_sys_agent, model: {@link SysAgent}
 * Created by hc on 2017/7/9.
 */
@Mapper
public interface SysAgentMapper extends CrudMapper<SysAgent, Long> {

	List<SysAgent> getWithPayOrderStatistic(Map<String, Object> params);

	List<SysAgent> findAgentRecruitNum();

	List<SysAgent> getNewInsert(Map<String,Object> params);

	@Update("UPDATE t_sys_agent SET F_SHOW_ANNOUNCE = TRUE WHERE F_SHOW_ANNOUNCE is FALSE")
	int resetShowAnnounce();

	@Update("UPDATE t_sys_agent SET F_IS_FINISH_INFO = FALSE  WHERE F_IS_FINISH_INFO is TRUE")
	int resetIsFinishInfo();

	SysAgent getService(Integer game);
	SysAgent pages(Integer gameId);

	SysAgent querySubset(Integer gameId);
	List<SysAgent> querySubsets(Map<String, Object> params);

	List<SysAgent> getPartner(Map<String, Object> params);

	int updateAgentId(String gameId);
	int updateAgentIds(String gameId);

	List<SysAgent> cancelPartner(Map<String, Object> params);


	List<SysAgent> giveAgentList(Map<String, Object> params);
	int updateAiveAgentList(Integer gameId);
	List<SysAgent> queryGiveAgentList(Map<String, Object> params);
	int updateNoAiveAgentList(Integer gameId);


	//start 金币消耗
	List<SysAgentVo> queryGoldlist(Map<String,Object> params);
	List<SysAgentVo> queryGoldAllAgent(Map<String,Object> params);

	List<SysAgentVo> queryGoldSubclass(@Param("list") List<Integer> gameId);
	List<SysAgentVo> queryGoldSubclassAll(@Param("list") List<Integer> gameId,@Param("startDate")String startDate,@Param("endDate")String endDate);
	//end 金币消耗



	//start 合伙人
	List<SysAgentVo> query4list(Map<String,Object> params);
	List<SysAgentVo> queryAllAgent(Map<String,Object> params);

	List<SysAgentVo> querysubclassAll(@Param("list") List<Integer> gameId);
	List<SysAgentVo> querysubclass(@Param("list") List<Integer> gameId,@Param("startDate")String startDate,@Param("endDate")String endDate);
	//end 合伙人
}
