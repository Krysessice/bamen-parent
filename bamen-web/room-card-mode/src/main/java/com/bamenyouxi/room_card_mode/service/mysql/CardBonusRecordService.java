package com.bamenyouxi.room_card_mode.service.mysql;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode.CardBonusRecordMapper;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.CardBonusRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardBonusRecordService extends AbstractCrudService<CardBonusRecord,Long>{

    @Autowired
    private CardBonusRecordMapper cardBonusRecordMapper;

    @Override
    public CrudMapper<CardBonusRecord, Long> getMapper() {
        return cardBonusRecordMapper;
    }

    @Override
    protected String sort() {
        return FieldConstant.SortConstant.CREATE_TIME_DESC;
    }
}
