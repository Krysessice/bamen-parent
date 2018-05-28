package com.bamenyouxi.cow_nn_mode.mapper.mysql.cow_nn_mode;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.CardGift4PlayerRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * mapper for table: t_card_gift_for_player_record, model: {@link CardGift4PlayerRecord}
 * Created by 13477 on 2017/8/23.
 */
@Mapper
public interface CardGift4PlayerRecordMapper extends CrudMapper<CardGift4PlayerRecord, Long> {

    CardGift4PlayerRecord queryNumTwo(String Account);

    List<CardGift4PlayerRecord> queryAgentPlayer(String params);

    List<CardGift4PlayerRecord> queryCardPlayer(Map<String, Object> params);
}
