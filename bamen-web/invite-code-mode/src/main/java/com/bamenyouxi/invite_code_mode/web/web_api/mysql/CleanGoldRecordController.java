package com.bamenyouxi.invite_code_mode.web.web_api.mysql;


import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.CleanGoldRecordMapper;

import com.bamenyouxi.invite_code_mode.service.mysql.CleanGoldRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/record")
public class CleanGoldRecordController {

    @Autowired
    private CleanGoldRecordService cleanGoldRecordService;

    @GetMapping("/Gold/list/")
    private WebResult getCleanGoldAll(
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
            @RequestParam Map<String, Object> params) {
        return WebResult.of(cleanGoldRecordService.getCleanGoldAll(page, size, params));
    }

}
