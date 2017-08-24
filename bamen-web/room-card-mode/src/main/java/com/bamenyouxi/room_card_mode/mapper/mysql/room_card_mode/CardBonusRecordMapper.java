package com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.CardBonusRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * mapper for table: t_card_bonus_record, model: {@link CardBonusRecord}
 * Created by 13477 on 2017/8/23.
 */
@Mapper
public interface CardBonusRecordMapper extends CrudMapper<CardBonusRecord, Long> {
}
