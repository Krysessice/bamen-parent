package com.bamenyouxi.invite_code_mode.mapper.sqlserver.record;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.invite_code_mode.model.sqlserver.account.AgentPartner;
import com.bamenyouxi.invite_code_mode.model.sqlserver.record.RoomParnterData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


@Mapper
public interface RoomParnterDataMapper extends CrudMapper<RoomParnterData, Integer> {

    List<RoomParnterData> queryPartnerMessage(Map<String,Object> params);

    List<RoomParnterData> queryAgentRoom(Map<String,Object> params);

    RoomParnterData querySumInnings(Map<String,Object> params);
}
