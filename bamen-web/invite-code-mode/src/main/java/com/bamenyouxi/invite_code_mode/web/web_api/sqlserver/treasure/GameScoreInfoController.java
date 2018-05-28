package com.bamenyouxi.invite_code_mode.web.web_api.sqlserver.treasure;

import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.impl.web.AbstractCrudController;

import com.bamenyouxi.core.model.result.WebResult;


import com.bamenyouxi.invite_code_mode.model.sqlserver.treasure.GameScoreInfo;
import com.bamenyouxi.invite_code_mode.service.sqlserver.GameScoreInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/update/")
public class GameScoreInfoController  {


    @Autowired
    private GameScoreInfoService gameScoreInfoService;


    @GetMapping("/user/gold/")
    private WebResult getWithrawal(
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
            @RequestParam Map<String, Object> params) {
        return WebResult.of(gameScoreInfoService.queryUserGoldScore(page,size,params));
    }

    @PutMapping("/gold/{score}/{gameId}")
    public WebResult updateUserGoldSocre(@PathVariable Integer score,@PathVariable Integer gameId){
        gameScoreInfoService.updateUserGoldSocre(score,gameId);
        return WebResult.of();
    }


}
