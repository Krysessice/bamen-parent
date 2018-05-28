package com.bamenyouxi.invite_code_mode.service.sqlserver.record;

import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.record.IntegralTurntableAwardListMapper;
import com.bamenyouxi.invite_code_mode.model.sqlserver.record.IntegralTurntableAwardList;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

@Service
public class IntegralTurntableAwardListService extends AbstractCrudService<IntegralTurntableAwardList, Integer> {

    @Autowired
    private IntegralTurntableAwardListMapper integralTurntableAwardListMapper;

    @Override
    public CrudMapper<IntegralTurntableAwardList, Integer> getMapper() {
        return integralTurntableAwardListMapper;
    }


    @Transactional
    public PageInfo<IntegralTurntableAwardList> getIntegral(int page, int size, Map<String,Object> params){
        List<IntegralTurntableAwardList> list=integralTurntableAwardListMapper.getIntegral(params);
        if (list.isEmpty() || list.get(0) == null)
            return new PageInfo<>();
        return new PageInfo<>(list);
    }

    @Transactional
    public void updateIntegral(double turnawardamount,double turnawardgoodamcout,int turnawardno ){
        int n=integralTurntableAwardListMapper.updateIntegral(turnawardamount,turnawardgoodamcout,turnawardno);
        Assert.isTrue(n>0, TipMsgConstant.OPERATION_FAILED);
    }
}
