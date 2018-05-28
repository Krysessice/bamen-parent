package com.bamenyouxi.invite_code_mode.web.web_api.sqlserver;


import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.impl.web.AbstractCrudController;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.invite_code_mode.model.sqlserver.account.AgentPartner;
import com.bamenyouxi.invite_code_mode.service.sqlserver.AgentPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/Partner")
public final class AgentPartnerController extends AbstractCrudController<AgentPartner ,Integer> {

    @Autowired
    private AgentPartnerService agentPartnerService;

    @Override
    public AbstractCrudService<AgentPartner, Integer> getService() {
        return agentPartnerService;
    }


    @GetMapping("/queryPartner/")
    private WebResult queryPartner(
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size
           ) {
        return WebResult.of(agentPartnerService.queryPartnerAgent(page,size));
    }

    @PostMapping("/saveAgent/{partnerGameid}/{qunid}")
    public WebResult saveAgent(@PathVariable int partnerGameid,@PathVariable int qunid){
        agentPartnerService.saveAgent(partnerGameid,qunid);
        return WebResult.of();
    }
}
