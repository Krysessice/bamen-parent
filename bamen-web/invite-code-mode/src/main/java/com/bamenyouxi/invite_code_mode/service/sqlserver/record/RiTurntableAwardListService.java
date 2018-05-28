package com.bamenyouxi.invite_code_mode.service.sqlserver.record;

import com.alibaba.fastjson.JSONObject;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.record.RiTurntableAwardListMapper;
import com.bamenyouxi.invite_code_mode.model.sqlserver.record.RiTurntableAwardList;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

@Service
public class RiTurntableAwardListService  extends AbstractCrudService<RiTurntableAwardList, Integer> {

    @Autowired
    private RiTurntableAwardListMapper riTurntableAwardListMapper;

    @Override
    public CrudMapper<RiTurntableAwardList, Integer> getMapper() {
        return riTurntableAwardListMapper;
    }


    @Transactional
    public PageInfo<RiTurntableAwardList> getDaily(int page, int size, Map<String,Object> params){
        List<RiTurntableAwardList> list=riTurntableAwardListMapper.getDaily(params);
        if (list.isEmpty() || list.get(0) == null)
            return new PageInfo<>();
        return new PageInfo<>(list);
    }

    @Transactional
    public JSONObject queryProbabilitySum(int turnawardno){
        DecimalFormat df   = new DecimalFormat("######0.00");
        RiTurntableAwardList turntableAwardLists=riTurntableAwardListMapper.getTurnAwardProbability(turnawardno);
        double sums=turntableAwardLists.getTurnawardprobability();
        double s=100-sums;
        String n=df.format(s);
        JSONObject result=new JSONObject();
        result.put("sums",sums);
        result.put("n",n);
        return  result;
    }

    @Transactional
    public void getTurnAwardProbability(int turnawardtype,double turnawardamount,double turnawardprobability,int turnawardno){
        RiTurntableAwardList turntableAwardLists=riTurntableAwardListMapper.getTurnAwardProbability(turnawardno);
        double n=turntableAwardLists.getTurnawardprobability();
        double sumGoodamcout=add(turnawardprobability,n);
        Assert.isTrue(sumGoodamcout<=100,TipMsgConstant.SYS_Probability);
        int s=riTurntableAwardListMapper.updateProbability(turnawardtype,turnawardamount,turnawardprobability,turnawardno);
        Assert.isTrue(s>0,TipMsgConstant.OPERATION_FAILED);
    }

    public double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }


}
