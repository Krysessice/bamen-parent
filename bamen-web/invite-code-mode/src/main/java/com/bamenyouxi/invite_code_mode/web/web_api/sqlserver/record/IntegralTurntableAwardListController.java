package com.bamenyouxi.invite_code_mode.web.web_api.sqlserver.record;

import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.impl.web.AbstractCrudController;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.invite_code_mode.constant.SysConstant;
import com.bamenyouxi.invite_code_mode.model.sqlserver.record.IntegralTurntableAwardList;
import com.bamenyouxi.invite_code_mode.service.sqlserver.record.IntegralTurntableAwardListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/Integral")
public class IntegralTurntableAwardListController  extends AbstractCrudController<IntegralTurntableAwardList,Integer> {

    @Autowired
    private IntegralTurntableAwardListService integralTurntableAwardListService;

    @Override
    public AbstractCrudService<IntegralTurntableAwardList, Integer> getService() {
        return integralTurntableAwardListService;
    }

    @GetMapping("/getIntegral/")
    private WebResult getIntegral(
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
            @RequestParam Map<String,Object> params){
        return WebResult.of(integralTurntableAwardListService.getIntegral(page,size,params));
    }

    @PutMapping("/updateIntegral/{turnawardamount}/{turnawardgoodamcout}/{turnawardno}/")
    public WebResult updateIntegral(@PathVariable double turnawardamount,
                                    @PathVariable double turnawardgoodamcout,@PathVariable int turnawardno){
        integralTurntableAwardListService.updateIntegral(turnawardamount,turnawardgoodamcout,turnawardno);
        return WebResult.of();
    }
}
