package com.bamenyouxi.cow_nn_mode.mapper.mysql.cow_nn_mode;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.CardGift4AgentRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * mapper for table: t_card_gift_for_agent_record, model: {@link CardGift4AgentRecord}
 * Created by 13477 on 2017/8/23.
 */
@Mapper
public interface CardGift4AgentRecordMapper extends CrudMapper<CardGift4AgentRecord, Long> {

   CardGift4AgentRecord queryNumOne(String Account);

   List<CardGift4AgentRecord> queryGiftAgent(String params);

   int  saves(CardGift4AgentRecord cardGift4AgentRecord);

   List<CardGift4AgentRecord> querypayforList(Map<String, Object> params);

}
