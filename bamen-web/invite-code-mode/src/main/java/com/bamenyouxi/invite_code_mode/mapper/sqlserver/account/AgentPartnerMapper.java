package com.bamenyouxi.invite_code_mode.mapper.sqlserver.account;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.invite_code_mode.model.sqlserver.account.AgentPartner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface AgentPartnerMapper extends CrudMapper<AgentPartner, Integer> {


    List<AgentPartner> queryPartnerAgent(int gameId);

    int saves(@Param("partnerGameid") int partnerGameid,@Param("qunid")  int qunid);

    int deletePartnerGameid(int partnerGameid);
}
