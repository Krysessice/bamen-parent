package com.bamenyouxi.room_card_mode.service.mysql;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode.CardGift4PlayerRecordMapper;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.CardGift4PlayerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardGift4PlayerRecordService  extends AbstractCrudService<CardGift4PlayerRecord ,Long>{

    @Autowired
    private CardGift4PlayerRecordMapper cardGift4PlayerRecordMapper;

    @Override
    public CrudMapper<CardGift4PlayerRecord, Long> getMapper() {
        return cardGift4PlayerRecordMapper;
    }
}
