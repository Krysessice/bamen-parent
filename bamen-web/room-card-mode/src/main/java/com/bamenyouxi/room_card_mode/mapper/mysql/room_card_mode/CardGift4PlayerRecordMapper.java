package com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.CardGift4PlayerRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * mapper for table: t_card_gift_for_player_record, model: {@link CardGift4PlayerRecord}
 * Created by 13477 on 2017/8/23.
 */
@Mapper
public interface CardGift4PlayerRecordMapper extends CrudMapper<CardGift4PlayerRecord, Long> {
}
