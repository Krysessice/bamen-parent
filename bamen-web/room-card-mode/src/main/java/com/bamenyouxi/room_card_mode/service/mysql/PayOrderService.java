package com.bamenyouxi.room_card_mode.service.mysql;


import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode.PayOrderMapper;
import com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode.SysAgentMapper;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.PayOrder;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.SysAgent;
import com.bamenyouxi.room_card_mode.util.CreateOrderNumber;
import com.bamenyouxi.room_card_mode.util.UserDetailsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;


@Service
public class PayOrderService extends AbstractCrudService<PayOrder,Long>{


    @Autowired
    private PayOrderMapper payOrderMapper;

    @Override
    public CrudMapper<PayOrder, Long> getMapper() {
        return payOrderMapper;
    }


    @Override
    protected String sort() {
        return FieldConstant.SortConstant.CREATE_TIME_DESC;
    }


    /**
     * 代理查看自己购卡记录
     */
    @Transactional
    public PageInfo<PayOrder> queryPayCard(int page, int size, String params){
        PageHelper.startPage(page,size,FieldConstant.SortConstant.CREATE_TIME_DESC);
        String id=UserDetailsUtil.getAccoutnt();
        List<PayOrder> list=payOrderMapper.queryPayCard(id);
        if (list.isEmpty() || list.get(0) == null)
            return new PageInfo<>();
        return new PageInfo<>(list);
    }

    @Transactional
    public PageInfo<PayOrder> getAgentSum(int page, int size, Map<String, Object> params) {
        super.listBefore(params);
        PageHelper.startPage(page, size, FieldConstant.SortConstant.CREATE_TIME_DESC);
        params.put(FieldConstant.CommonFieldConstant.groupBy.name(), FieldConstant.GroupConstant.CREATE_DATED);
        List<PayOrder> list = payOrderMapper.getAgentSum(params);
        if (list.isEmpty() || list.get(0) == null)
            return new PageInfo<>();
        return new PageInfo<>(list);
    }

    @Transactional
    public PageInfo<PayOrder> getAgent(int page, int size, Map<String, Object> params) {
        super.listBefore(params);
        PageHelper.startPage(page, size, FieldConstant.SortConstant.CREATE_TIME_DESC);
        params.put(FieldConstant.CommonFieldConstant.groupBy.name(), FieldConstant.GroupConstant.CREATE_DATED);
        List<PayOrder> list = payOrderMapper.getAgent(params);
        if (list.isEmpty() || list.get(0) == null)
            return new PageInfo<>();
        return new PageInfo<>(list);
    }

    @Transactional
    public PageInfo<PayOrder> queryCardSum(int page, int size, Map<String, Object> params) {
        super.listBefore(params);
        PageHelper.startPage(page, size, FieldConstant.SortConstant.CREATE_TIME_DESC);
        params.put(FieldConstant.CommonFieldConstant.groupBy.name(), FieldConstant.GroupConstant.CREATE_DATED);
        List<PayOrder> list = payOrderMapper.getsums(params);
        if (list.isEmpty() || list.get(0) == null)
            return new PageInfo<>();
        return new PageInfo<>(list);
    }

    @Transactional
    public PageInfo<PayOrder> getSumCard(int page, int size, String account) {
        PageHelper.startPage(page, size, FieldConstant.SortConstant.CREATE_TIME_DESCS);
        List<PayOrder> list = payOrderMapper.getSumCard(UserDetailsUtil.getAccoutnt());
        if (list.isEmpty() || list.get(0) == null)
            return new PageInfo<>();
        return new PageInfo<>(list);
    }


    @Transactional
    public PageInfo<PayOrder> queryCardforList(int page, int size, Map<String, Object> params) {
        super.listBefore(params);
        PageHelper.startPage(page, size, FieldConstant.SortConstant.CREATE_TIME_DESC);
        params.put(FieldConstant.CommonFieldConstant.groupBy.name(), FieldConstant.GroupConstant.CREATE_DATED);
        List<PayOrder> list = payOrderMapper.queryCardforList(params);
        if (list.isEmpty() || list.get(0) == null)
            return new PageInfo<>();
        return new PageInfo<>(list);
    }

}
