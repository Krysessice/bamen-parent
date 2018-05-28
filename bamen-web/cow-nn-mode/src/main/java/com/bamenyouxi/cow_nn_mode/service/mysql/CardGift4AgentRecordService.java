package com.bamenyouxi.cow_nn_mode.service.mysql;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.cow_nn_mode.mapper.mysql.cow_nn_mode.CardGift4AgentRecordMapper;
import com.bamenyouxi.cow_nn_mode.mapper.mysql.cow_nn_mode.SysAgentMapper;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.CardGift4AgentRecord;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.SysAgent;
import com.bamenyouxi.cow_nn_mode.util.UserDetailsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

@Service
public class CardGift4AgentRecordService extends AbstractCrudService<CardGift4AgentRecord,Long> {

    @Autowired
    private CardGift4AgentRecordMapper cardGift4AgentRecordMapper;

    @Autowired
    private SysAgentMapper sysAgentMapper;

    @Override
    public CrudMapper<CardGift4AgentRecord, Long> getMapper() {
        return cardGift4AgentRecordMapper;
    }


    /**
     * 代理赠卡代理记录
     */
    @Transactional
    public PageInfo<CardGift4AgentRecord> queryGiftAgent(int page, int size, String params){
        PageHelper.startPage(page,size, FieldConstant.SortConstant.CREATE_TIME_DESC);
        String id= UserDetailsUtil.getAccoutnt();
        List<CardGift4AgentRecord> list=cardGift4AgentRecordMapper.queryGiftAgent(id);
        if (list.isEmpty() || list.get(0) == null)
            return new PageInfo<>();
        return new PageInfo<>(list);
    }

    @Transactional
    public PageInfo<CardGift4AgentRecord> querypayforList(int page, int size, Map<String, Object> params) {
        super.listBefore(params);
        PageHelper.startPage(page, size, FieldConstant.SortConstant.CREATE_TIME_DESC);
        params.put(FieldConstant.CommonFieldConstant.groupBy.name(), FieldConstant.GroupConstant.CREATE_DATED);
        List<CardGift4AgentRecord> list = cardGift4AgentRecordMapper.querypayforList(params);
        if (list.isEmpty() || list.get(0) == null)
            return new PageInfo<>();
        return new PageInfo<>(list);
    }

    /**
     * 给代理赠卡
     */
    @Transactional
    public void saves(CardGift4AgentRecord cardGift4AgentRecord){
        List<SysAgent> sysAgents= sysAgentMapper.getAccount(cardGift4AgentRecord.getPresentee().toString());
        if(sysAgents.isEmpty()){
            Assert.isTrue(sysAgents.size()<0, TipMsgConstant.SYS_AGENT);
        }
        cardGift4AgentRecord.setPresenter(Long.valueOf(UserDetailsUtil.getAccoutnt()));
        int n=cardGift4AgentRecordMapper.saves(cardGift4AgentRecord);
        Assert.isTrue(n>0,TipMsgConstant.OPERATION_FAILED);
    }


}
