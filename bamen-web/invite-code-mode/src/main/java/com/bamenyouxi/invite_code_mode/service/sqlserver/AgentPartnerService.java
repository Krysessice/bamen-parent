package com.bamenyouxi.invite_code_mode.service.sqlserver;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.account.AgentPartnerMapper;
import com.bamenyouxi.invite_code_mode.model.sqlserver.account.AgentPartner;
import com.bamenyouxi.invite_code_mode.model.sqlserver.account.GroupRoom;
import com.bamenyouxi.invite_code_mode.util.UserDetailsUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AgentPartnerService  extends AbstractCrudService<AgentPartner, Integer> {

    @Autowired
    AgentPartnerMapper agentPartnerMapperl;


    @Override
    public CrudMapper<AgentPartner, Integer> getMapper() {
        return agentPartnerMapperl;
    }

    @Transactional
    public PageInfo<AgentPartner> queryPartnerAgent(int page, int size){
        List<AgentPartner> list=agentPartnerMapperl.queryPartnerAgent(UserDetailsUtil.getGameId());
        if (list.isEmpty() || list.get(0) == null)
            return new PageInfo<>();
        return new PageInfo<>(list);
    }

    @Transactional
    public void saveAgent(int partnerGameid,int qunid){
        List<AgentPartner> list = getMapper().get(new HashMap<String, Object>() {{
            put("partnerGameid", partnerGameid);
            put("qunid",qunid);
        }});
        Assert.isTrue(list.isEmpty(), TipMsgConstant.SYS_PESION);
        int n=agentPartnerMapperl.saves(partnerGameid,qunid);
        Assert.isTrue(n>0, TipMsgConstant.PARAM_INVALID);
    }

}
