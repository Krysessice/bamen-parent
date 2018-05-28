package com.bamenyouxi.invite_code_mode.web.web_api.sqlserver.record;

import com.alibaba.fastjson.JSONObject;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.impl.web.AbstractCrudController;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.invite_code_mode.constant.SysConstant;
import com.bamenyouxi.invite_code_mode.model.sqlserver.record.UseridScoreRoomnumSave;
import com.bamenyouxi.invite_code_mode.service.sqlserver.record.UseridScoreRoomnumSaveService;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UseridScoreRoomnumSaveController  extends AbstractCrudController<UseridScoreRoomnumSave,Integer> {

    @Autowired
    private UseridScoreRoomnumSaveService useridScoreRoomnumSaveService;

    @Override
    public AbstractCrudService<UseridScoreRoomnumSave, Integer> getService() {
        return useridScoreRoomnumSaveService;
    }


    @GetMapping("/heade/")
    @JsonSerialize
    public @ResponseBody JSONObject getUser(@RequestParam Map<String,Object> params){
        return  useridScoreRoomnumSaveService.getUser(params);
    }


    @GetMapping("/getUserScore/")
    private WebResult getUserScore(
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
            @RequestParam Map<String,Object> params){
        return WebResult.of(useridScoreRoomnumSaveService.getUserScore(page,size,params));
    }

    @GetMapping("/countKind/")
    private WebResult countKind(
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
            @RequestParam Map<String,Object> params){
        return WebResult.of(useridScoreRoomnumSaveService.countKind(page,size,params));
    }

    @GetMapping("/queryAllRoom/")
    private WebResult queryAllRoom(
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
            @RequestParam Map<String,Object> params){
        return WebResult.of(useridScoreRoomnumSaveService.queryAllRoom(page,size,params));
    }

    @GetMapping("/queryPartnerAgent/")
    private WebResult queryPartnerAgent(
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
            @RequestParam Map<String,Object> params){
        return WebResult.of(useridScoreRoomnumSaveService.queryPartnerAgent(page,size,params));
    }


    @GetMapping("/querySumPartnerAgent/")
    @JsonSerialize
    public @ResponseBody JSONObject querySumPartnerAgent(@RequestParam Map<String,Object> params){
        return  useridScoreRoomnumSaveService.querySumPartnerAgent(params);
    }



    
}
