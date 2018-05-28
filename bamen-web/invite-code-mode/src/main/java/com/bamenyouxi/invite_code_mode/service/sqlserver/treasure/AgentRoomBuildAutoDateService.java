package com.bamenyouxi.invite_code_mode.service.sqlserver.treasure;

import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.treasure.AgentRoomBuildAutoDateMapper;
import com.bamenyouxi.invite_code_mode.model.sqlserver.treasure.AgentRoomBuildAutoDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class AgentRoomBuildAutoDateService extends AbstractCrudService<AgentRoomBuildAutoDate, Integer> {

    @Autowired
    private AgentRoomBuildAutoDateMapper agentRoomBuildAutoDateMapper;

    @Override
    public CrudMapper<AgentRoomBuildAutoDate, Integer> getMapper() {
        return agentRoomBuildAutoDateMapper;
    }


    @Transactional
    public void setUp(){
        int n=agentRoomBuildAutoDateMapper.setUp();
        Assert.isTrue(n>0, TipMsgConstant.OPERATION_SUCCESS);
    }

}
