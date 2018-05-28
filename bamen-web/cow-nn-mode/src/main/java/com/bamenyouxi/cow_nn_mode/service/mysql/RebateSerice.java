package com.bamenyouxi.cow_nn_mode.service.mysql;

import com.alibaba.fastjson.JSONObject;
import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.cow_nn_mode.mapper.mysql.cow_nn_mode.RebateMapper;
import com.bamenyouxi.cow_nn_mode.mapper.mysql.cow_nn_mode.WithdrawMapper;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.Price;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.Rebate;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.SysAgent;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.Withdraw;
import com.bamenyouxi.cow_nn_mode.util.UserDetailsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RebateSerice extends AbstractCrudService<Rebate, Long> {

    @Autowired
    private RebateMapper rebateMapper;

    @Autowired
    private WithdrawMapper withdrawMapper;
    @Override
    public CrudMapper<Rebate, Long> getMapper() {
        return rebateMapper;
    }


    @Transactional
    public PageInfo<Rebate> getDetail(int page, int size, Map<String,Object> params){
        params.put(FieldConstant.ModelFieldConstant.favorerAccount.name(),UserDetailsUtil.getAccoutnt());
        PageHelper.startPage(page,size, FieldConstant.SortConstant.CREATE_TIME_DESC);
        List<Rebate> list=rebateMapper.getDetail(params);
        if (list.isEmpty() || list.get(0) == null)
            return new PageInfo<>();
        return new PageInfo<>(list);
    }

    @Transactional
    public JSONObject getDetailSum(Map<String ,Object> params){
        params.put(FieldConstant.ModelFieldConstant.favorerAccount.name(),UserDetailsUtil.getAccoutnt());
        Rebate rebate=rebateMapper.getDetailSum(params);
        Withdraw withdraw=withdrawMapper.getWithdraw(new HashMap<String, Object>(){{
            put(FieldConstant.CommonFieldConstant.account.name(),UserDetailsUtil.getAccoutnt());
        }});//提现表
        Rebate rebateWithdraw=rebateMapper.getDetailSum(new HashMap<String, Object>(){{
            put(FieldConstant.ModelFieldConstant.favorerAccount.name(),UserDetailsUtil.getAccoutnt());
        }});//比例表
        BigDecimal sum=rebateWithdraw.getRebateMoney().subtract(withdraw.getWithdraw());
        BigDecimal n=rebate.getPayPrice();
        BigDecimal s=rebate.getRebateMoney();
        JSONObject result=new JSONObject();
        result.put("n",n);
        result.put("s",s);
        result.put("sum",sum);
        return result;
    }

}
