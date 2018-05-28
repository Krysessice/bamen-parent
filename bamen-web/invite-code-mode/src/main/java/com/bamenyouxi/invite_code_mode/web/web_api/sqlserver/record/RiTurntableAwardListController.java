package com.bamenyouxi.invite_code_mode.web.web_api.sqlserver.record;

import com.alibaba.fastjson.JSONObject;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.impl.web.AbstractCrudController;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.invite_code_mode.constant.SysConstant;
import com.bamenyouxi.invite_code_mode.model.sqlserver.record.RiTurntableAwardList;
import com.bamenyouxi.invite_code_mode.service.sqlserver.record.RiTurntableAwardListService;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/Daily")
public class RiTurntableAwardListController extends AbstractCrudController<RiTurntableAwardList,Integer> {

    @Autowired
    private RiTurntableAwardListService riTurntableAwardListService;

    @Override
    public AbstractCrudService<RiTurntableAwardList, Integer> getService() {
        return riTurntableAwardListService;
    }


    @GetMapping("/getDaily/")
    private WebResult getDaily(
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
            @RequestParam Map<String,Object> params){
        return WebResult.of(riTurntableAwardListService.getDaily(page,size,params));
    }


    @GetMapping("/getProbability/{turnawardno}/")
    @JsonSerialize
    public  @ResponseBody JSONObject queryProbabilitySum(@PathVariable int turnawardno){
        return riTurntableAwardListService.queryProbabilitySum(turnawardno);
    }


    @PutMapping("/UpdateTurnAwardProbability/{turnawardtype}/{turnawardamount}/{turnawardprobability}/{turnawardno}")
    public WebResult getTurnAwardProbability(@PathVariable int turnawardtype,@PathVariable double turnawardamount,
                                             @PathVariable double turnawardprobability,@PathVariable int turnawardno ){
        riTurntableAwardListService.getTurnAwardProbability(turnawardtype,turnawardamount,turnawardprobability,turnawardno);
        return WebResult.of();
    }

}
