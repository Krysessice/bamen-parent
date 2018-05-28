package com.bamenyouxi.invite_code_mode.web.web_api.sqlserver.record;


import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.impl.web.AbstractCrudController;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.invite_code_mode.constant.SysConstant;
import com.bamenyouxi.invite_code_mode.model.sqlserver.record.RedeemCode;
import com.bamenyouxi.invite_code_mode.service.sqlserver.record.RedeemCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/redeem")
public class RedeemCodeController extends AbstractCrudController<RedeemCode,Integer> {


    @Autowired
    private RedeemCodeService redeemCodeService;

    @Override
    public AbstractCrudService<RedeemCode, Integer> getService() {
        return redeemCodeService;
    }


    @PostMapping("/add/{code}/{card}/{endtime}")
    public WebResult RedeemCode(@PathVariable String code,@PathVariable Integer card,@PathVariable Timestamp endtime){
        redeemCodeService.RedeemCode(code,card,endtime);
        return  WebResult.of();
    }


    @GetMapping("/queryAllList/")
    private WebResult queryAllList(
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
            @RequestParam Map<String,Object> params){
        return WebResult.of(redeemCodeService.queryAllList(page,size,params));
    }

    @GetMapping("/queryPastNoAllList/")
    private WebResult queryPastNoAllList(
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
            @RequestParam Map<String,Object> params){
        return WebResult.of(redeemCodeService.queryPastNoAllList(page,size,params));
    }
}
