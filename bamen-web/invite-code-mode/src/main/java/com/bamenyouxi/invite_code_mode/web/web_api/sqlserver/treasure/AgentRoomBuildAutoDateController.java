package com.bamenyouxi.invite_code_mode.web.web_api.sqlserver.treasure;


import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.impl.web.AbstractCrudController;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.invite_code_mode.model.sqlserver.record.RedeemCode;
import com.bamenyouxi.invite_code_mode.model.sqlserver.treasure.AgentRoomBuildAutoDate;
import com.bamenyouxi.invite_code_mode.service.sqlserver.record.RedeemCodeService;
import com.bamenyouxi.invite_code_mode.service.sqlserver.treasure.AgentRoomBuildAutoDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/AgentRoom")
public class AgentRoomBuildAutoDateController extends AbstractCrudController<AgentRoomBuildAutoDate,Integer> {


    @Autowired
    private AgentRoomBuildAutoDateService agentRoomBuildAutoDateService;

    @Override
    public AbstractCrudService<AgentRoomBuildAutoDate, Integer> getService() {
        return agentRoomBuildAutoDateService;
    }


    @PutMapping("/setUp/")
    public WebResult setUp(){
        agentRoomBuildAutoDateService.setUp();
        return  WebResult.of();
    }

}
