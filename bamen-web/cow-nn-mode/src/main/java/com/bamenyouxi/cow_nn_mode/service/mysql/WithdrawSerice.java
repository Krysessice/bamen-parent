package com.bamenyouxi.cow_nn_mode.service.mysql;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.cow_nn_mode.mapper.mysql.cow_nn_mode.RebateMapper;
import com.bamenyouxi.cow_nn_mode.mapper.mysql.cow_nn_mode.SysAgentMapper;
import com.bamenyouxi.cow_nn_mode.mapper.mysql.cow_nn_mode.WithdrawMapper;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.Rebate;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.SysAgent;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.Withdraw;
import com.bamenyouxi.cow_nn_mode.util.UserDetailsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Service
public class WithdrawSerice extends AbstractCrudService<Withdraw, Long> {

    @Autowired
    private WithdrawMapper withdrawMapper;
    @Autowired
    private RebateMapper rebateMapper;
    @Autowired
    private SysAgentMapper sysAgentMapper;

    @Override
    public CrudMapper<Withdraw, Long> getMapper() {
        return withdrawMapper;
    }

    @Transactional
    public void addWithdraw(BigDecimal withdraw){
        List<SysAgent> list=sysAgentMapper.get(new HashMap<String, Object>(){{
            put(FieldConstant.DBFieldConstant.F_ACCOUNT.name(),UserDetailsUtil.getAccoutnt());
        }});
        boolean b=list.get(0).getFinishInfo();
        Assert.isTrue(b,TipMsgConstant.SYS_AGENT_MESSAGE);

        Withdraw withdrawEntity=withdrawMapper.getWithdraw(new HashMap<String, Object>(){{
            put(FieldConstant.DBFieldConstant.F_ACCOUNT.name(),UserDetailsUtil.getAccoutnt());
        }});//提现表
        Rebate rebateWithdraw=rebateMapper.getDetailSum(new HashMap<String, Object>(){{
            put(FieldConstant.ModelFieldConstant.favorerAccount.name(),UserDetailsUtil.getAccoutnt());
        }});//比例表
        BigDecimal sum=rebateWithdraw.getRebateMoney().subtract(withdrawEntity.getWithdraw());
        int ten=withdraw.compareTo(BigDecimal.valueOf(100));
        Assert.isTrue(ten>=0,TipMsgConstant.SYS_WITHDRAW_TEN);

        int flag=withdraw.compareTo(sum);
        Assert.isTrue(flag<=0,TipMsgConstant.SYS_WITHDRAW_MONEY);

        Withdraw withdraw1=new Withdraw();
        withdraw1.setAccount(UserDetailsUtil.getAccoutnt());
        withdraw1.setWithdraw(withdraw);
        int n=withdrawMapper.add(withdraw1);
        Assert.isTrue(n>0, TipMsgConstant.OPERATION_FAILED);
    }



}
