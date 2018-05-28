package com.bamenyouxi.invite_code_mode.web.web_api.mysql;

import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.impl.web.AbstractCrudController;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.UserGold;
import com.bamenyouxi.invite_code_mode.service.mysql.UserGoldSerice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/User/team")
public class UserGoldController extends AbstractCrudController<UserGold, Long> {

    @Autowired
    private UserGoldSerice userGoldSerice;

    @Override
    public AbstractCrudService<UserGold, Long> getService() {
        return userGoldSerice;
    }

    @GetMapping("/Gold/list/")
    private WebResult sumActual(
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
            @RequestParam Map<String, Object> params) {
        return WebResult.of(userGoldSerice.sumActual(page, size, params));
    }


//    @GetMapping("/Gold/queryList/")
//    private WebResult queryList(
//            @RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
//            @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
//            @RequestParam Map<String, Object> params) {
//        return WebResult.of(userGoldSerice.queryList(page, size,params));
//    }


}
