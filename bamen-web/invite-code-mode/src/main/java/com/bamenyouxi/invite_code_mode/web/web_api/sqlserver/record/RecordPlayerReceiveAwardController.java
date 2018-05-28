package com.bamenyouxi.invite_code_mode.web.web_api.sqlserver.record;

import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.impl.web.AbstractCrudController;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.invite_code_mode.constant.SysConstant;
import com.bamenyouxi.invite_code_mode.model.sqlserver.record.IntegralTurntableAwardList;
import com.bamenyouxi.invite_code_mode.model.sqlserver.record.RecordPlayerReceiveAward;
import com.bamenyouxi.invite_code_mode.service.sqlserver.record.RecordPlayerReceiveAwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/cash")
public class RecordPlayerReceiveAwardController extends AbstractCrudController<RecordPlayerReceiveAward,Integer> {

    @Autowired
    private RecordPlayerReceiveAwardService recordPlayerReceiveAwardService;

    @Override
    public AbstractCrudService<RecordPlayerReceiveAward, Integer> getService() {
        return recordPlayerReceiveAwardService;
    }

    @GetMapping("/getCashRecord/")
    private WebResult getCashRecord(
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
            @RequestParam Map<String,Object> params){
        return WebResult.of(recordPlayerReceiveAwardService.getCashRecord(page,size,params));
    }

}
