package com.bamenyouxi.cow_nn_mode.service.mysql;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.cow_nn_mode.mapper.mysql.cow_nn_mode.CardGift4PlayerRecordMapper;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.CardGift4AgentRecord;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.CardGift4PlayerRecord;
import com.bamenyouxi.cow_nn_mode.util.UserDetailsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class CardGift4PlayerRecordService{

    @Autowired
    private CardGift4PlayerRecordMapper cardGift4PlayerRecordMapper;


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
        /*super.listBefor(params);*/
        PageHelper.startPage(page, size, FieldConstant.SortConstant.CREATE_TIME_DESC);
        params.put(FieldConstant.CommonFieldConstant.groupBy.name(), FieldConstant.GroupConstant.CREATE_DATED);
        List<CardGift4PlayerRecord> list = cardGift4PlayerRecordMapper.queryCardPlayer(params);
        if (list.isEmpty() || list.get(0) == null)
            return new PageInfo<>();
        return new PageInfo<>(list);
    }

}
