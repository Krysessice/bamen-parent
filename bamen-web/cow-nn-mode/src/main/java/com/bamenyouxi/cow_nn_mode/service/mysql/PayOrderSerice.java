package com.bamenyouxi.cow_nn_mode.service.mysql;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.cow_nn_mode.mapper.mysql.cow_nn_mode.PayOrderMapper;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.PayOrder;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.SysAgent;
import com.bamenyouxi.cow_nn_mode.util.UserDetailsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class PayOrderSerice  extends AbstractCrudService<PayOrder, Long> {

    @Autowired
    private PayOrderMapper payOrderMapper;

    @Override
    public CrudMapper<PayOrder, Long> getMapper() {
        return payOrderMapper;
    }


    @Transactional
    public PageInfo<PayOrder> getPayAgent(int page, int size, Map<String,Object> params){
        PageHelper.startPage(page,size, FieldConstant.SortConstant.CREATE_TIME_DESCB);
        params.put(FieldConstant.ModelFieldConstant.superAgentId.name(), UserDetailsUtil.getAccoutnt());
        List<PayOrder> list=payOrderMapper.getPayAgent(params);
        if (list.isEmpty() || list.get(0) == null)
            return new PageInfo<>();
        return new PageInfo<>(list);
    }


    public PageInfo<PayOrder> getPayDay(int page, int size, Map<String, Object> params) {
        PageHelper.startPage(page,size, FieldConstant.SortConstant.CREATE_TIME_DESC);
        List<PayOrder> list=payOrderMapper.getPayDay(params);
        if (list.isEmpty() || list.get(0) == null)
            return new PageInfo<>();
        return new PageInfo<>(list);
    }

    public PageInfo<PayOrder> getPayList(int page, int size, Map<String, Object> params) {
        PageHelper.startPage(page,size, FieldConstant.SortConstant.CREATE_TIME_DESC);
        params.put(FieldConstant.CommonFieldConstant.sysAgentId.name(),UserDetailsUtil.getAccoutnt());
        List<PayOrder> list=payOrderMapper.getAgentPayList(params);
        if (list.isEmpty() || list.get(0) == null)
            return new PageInfo<>();
        return new PageInfo<>(list);
    }

}
