package com.bamenyouxi.room_card_mode.service.mysql;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode.CardBonusRecordMapper;
import com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode.CardGift4AgentRecordMapper;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.CardGift4AgentRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CardGift4AgentRecordService  extends AbstractCrudService<CardGift4AgentRecord,Long>{

    @Autowired
    private CardGift4AgentRecordMapper cardGift4AgentRecordMapper;

    @Override
    public CrudMapper<CardGift4AgentRecord, Long> getMapper() {
        return cardGift4AgentRecordMapper;
    }



}
