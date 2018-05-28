package com.bamenyouxi.room_card_mode.service.mysql;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode.CardBonusRecordMapper;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.CardBonusRecord;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.SysAgent;
import com.bamenyouxi.room_card_mode.util.UserDetailsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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


    @Transactional
    public PageInfo<CardBonusRecord> queryAll(int page, int size, Map<String,Object> params){
        PageHelper.startPage(page,size, FieldConstant.SortConstant.CREATE_TIME_DESC);
        params.put(FieldConstant.CommonFieldConstant.groupBy.name(),FieldConstant.GroupConstant.CREATE_DATED);
        List<CardBonusRecord> list=cardBonusRecordMapper.queryAll(params);
        if (list.isEmpty() || list.get(0) == null)
            return new PageInfo<>();
        return new PageInfo<>(list);
    }


    @Transactional
    public PageInfo<CardBonusRecord> queryOne(int page, int size, String params){
        PageHelper.startPage(page,size, FieldConstant.SortConstant.CREATE_TIME_DESC);
        String id= UserDetailsUtil.getAccoutnt();
        List<CardBonusRecord> list=cardBonusRecordMapper.queryOne(id);
        if (list.isEmpty() || list.get(0) == null)
            return new PageInfo<>();
        return new PageInfo<>(list);
    }


    @Transactional
    public PageInfo<CardBonusRecord> queryTwo(int page, int size, String params){
        PageHelper.startPage(page,size, FieldConstant.SortConstant.CREATE_TIME_DESC);
        String id= UserDetailsUtil.getAccoutnt();
        List<CardBonusRecord> list=cardBonusRecordMapper.queryTwo(id);
        if (list.isEmpty() || list.get(0) == null)
            return new PageInfo<>();
        return new PageInfo<>(list);
    }
}
