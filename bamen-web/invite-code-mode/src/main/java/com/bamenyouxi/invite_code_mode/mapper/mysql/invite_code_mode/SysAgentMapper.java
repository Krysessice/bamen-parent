package com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SysAgent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

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
}
