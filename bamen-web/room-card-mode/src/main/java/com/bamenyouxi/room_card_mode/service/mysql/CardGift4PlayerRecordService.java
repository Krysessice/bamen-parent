package com.bamenyouxi.room_card_mode.service.mysql;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode.CardGift4PlayerRecordMapper;
import com.bamenyouxi.room_card_mode.mapper.sqlserver.treasure.GameScoreInfoMapper;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.CardGift4PlayerRecord;
import com.bamenyouxi.room_card_mode.model.sqlserver.treasure.GameScoreInfo;
import com.bamenyouxi.room_card_mode.util.UserDetailsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CardGift4PlayerRecordService  extends AbstractCrudService<CardGift4PlayerRecord,Long>{

    @Autowired
    private CardGift4PlayerRecordMapper cardGift4PlayerRecordMapper;

    @Override
    public CrudMapper<CardGift4PlayerRecord, Long> getMapper() {
        return cardGift4PlayerRecordMapper;
    }

    /**
     * 代理赠卡玩家记录
     */
    @Transactional
    public PageInfo<CardGift4PlayerRecord> queryAgentPlayer(int page, int size, String params){
        PageHelper.startPage(page,size, FieldConstant.SortConstant.CREATE_TIME_DESC);
        String id= UserDetailsUtil.getAccoutnt();
        List<CardGift4PlayerRecord> list=cardGift4PlayerRecordMapper.queryAgentPlayer(id);
        if (list.isEmpty() || list.get(0) == null)
            return new PageInfo<>();
        return new PageInfo<>(list);
    }

    @Transactional
    public PageInfo<CardGift4PlayerRecord> queryCardPlayer(int page, int size, Map<String, Object> params) {
        super.listBefore(params);
        PageHelper.startPage(page, size, FieldConstant.SortConstant.CREATE_TIME_DESC);
        params.put(FieldConstant.CommonFieldConstant.groupBy.name(), FieldConstant.GroupConstant.CREATE_DATED);
        List<CardGift4PlayerRecord> list = cardGift4PlayerRecordMapper.queryCardPlayer(params);
        if (list.isEmpty() || list.get(0) == null)
            return new PageInfo<>();
        return new PageInfo<>(list);
    }

}
