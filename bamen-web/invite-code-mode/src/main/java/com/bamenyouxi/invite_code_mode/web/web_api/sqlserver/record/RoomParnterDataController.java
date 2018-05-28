package com.bamenyouxi.invite_code_mode.web.web_api.sqlserver.record;


import com.alibaba.fastjson.JSONObject;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.impl.web.AbstractCrudController;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.invite_code_mode.model.sqlserver.record.RoomParnterData;
import com.bamenyouxi.invite_code_mode.service.sqlserver.record.RoomParnterDataService;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/room")
public final class RoomParnterDataController extends AbstractCrudController<RoomParnterData,Integer> {

    @Autowired
    private RoomParnterDataService roomParnterDataService;

    @Override
    public AbstractCrudService<RoomParnterData, Integer> getService() {
        return roomParnterDataService;
    }


    @GetMapping("/queryPartnerMessage/")
    private WebResult queryPartnerMessage(
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
            @RequestParam Map<String,Object> params){
        return WebResult.of(roomParnterDataService.queryPartnerMessage(page,size,params));
    }

    @GetMapping("/queryAgentRoom/")
    private WebResult queryAgentRoom(
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
            @RequestParam Map<String,Object> params){
        return WebResult.of(roomParnterDataService.queryAgentRoom(page,size,params));
    }

//    @GetMapping("/querySumInnings/")
//    @JsonSerialize
//    public @ResponseBody JSONObject roomParnterDataMapper(@RequestParam Map<String,Object> params){
//        return  roomParnterDataService.querySumInnings(params);
//    }

}
