package com.bamenyouxi.cow_nn_mode.web.web_api.mysql;

import com.alibaba.fastjson.JSONObject;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.impl.web.AbstractCrudController;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.Price;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.Rebate;
import com.bamenyouxi.cow_nn_mode.service.mysql.RebateSerice;
import com.bamenyouxi.cow_nn_mode.service.mysql.WithdrawSerice;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/rebate")
public class RebateController extends AbstractCrudController<Rebate, Long> {

    @Autowired
    private RebateSerice rebateSerice;
    @Autowired
    private WithdrawSerice withdrawSerice;

    @Override
    public AbstractCrudService<Rebate, Long> getService() {
        return rebateSerice;
    }

    @GetMapping("/getDetail/")
    public WebResult getDetail(
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
            @RequestParam Map<String ,Object> params){
        return WebResult.of(rebateSerice.getDetail(page,size,params));
    }

    @GetMapping("/getDetailSum/")
    @JsonSerialize
    public @ResponseBody
    JSONObject getDetailSum(@RequestParam Map<String ,Object> params){
        return  rebateSerice.getDetailSum(params);
    }



}
