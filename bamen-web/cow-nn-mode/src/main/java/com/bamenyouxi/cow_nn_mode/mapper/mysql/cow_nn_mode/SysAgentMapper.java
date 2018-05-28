package com.bamenyouxi.cow_nn_mode.mapper.mysql.cow_nn_mode;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.SysAgent;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.SysAgentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

/**
 * mapper for table: t_sys_agent, model: {@link SysAgent}
 * Created by hc on 2017/7/9.
 */
@Mapper
public interface SysAgentMapper extends CrudMapper<SysAgent, Long> {

    List<SysAgent> gets(@Param("params") Map<String,Object> params);
    int updateFreeze(SysAgent sysAgent);
    List getSuperAgentID(String account);
    List<SysAgent> getOfficeAgentPayMessage(Map<String,Object> params);
    SysAgent getOfficeAgentSumPayMessage(Map<String,Object> params);
    List<SysAgent> getSalesman(Map<String,Object> params);
    SysAgent getSalesmanSum(Map<String,Object> params);
    List<SysAgent> salesmanPayOrderModel(Map<String,Object> params);
    SysAgent getSalesmanPayOrderSum(Map<String,Object> params);
    SysAgent updateMessages(SysAgent sysAgent);
    List<SysAgent> getAccount(String F_ACCOUNT);
    List<SysAgent> queryMun(String account);
    List<SysAgent> getNewInsert(Map<String,Object> params);
    SysAgent getJurisdiction(String account);


    int OpenOffice(SysAgent t);

    int saveAll(SysAgent sysAgent);

    int updateAgentId(String account);

    int updateAgentIds(String account);




    List<SysAgent> getId(String F_ACCOUNT);

    List<SysAgent> getAll(String F_ACCOUNT);





    int updates(SysAgent t);







    List<SysAgentVo> querysubclass(@Param("list") List<String> gameId,@Param("startDate")String startDate,@Param("endDate")String endDate);

}
