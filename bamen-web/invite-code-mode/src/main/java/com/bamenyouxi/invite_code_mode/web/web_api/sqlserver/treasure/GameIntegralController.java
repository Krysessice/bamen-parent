package com.bamenyouxi.invite_code_mode.web.web_api.sqlserver.treasure;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.impl.web.AbstractCrudController;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.invite_code_mode.model.sqlserver.treasure.AgentRoomBuildAutoDate;
import com.bamenyouxi.invite_code_mode.model.sqlserver.treasure.GameIntegral;
import com.bamenyouxi.invite_code_mode.service.sqlserver.treasure.GameIntegralService;
import com.bamenyouxi.invite_code_mode.util.UserDetailsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/rmb/")
public class GameIntegralController extends AbstractCrudController<GameIntegral,Integer> {


    @Autowired
    private GameIntegralService gameIntegralService;

    @Override
    public AbstractCrudService<GameIntegral, Integer> getService() {
        return gameIntegralService;
    }

    @GetMapping("/getWithrawal/")
    private WebResult getWithrawal(
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
            @RequestParam Map<String, Object> params) {
        return WebResult.of(gameIntegralService.getWithrawal(page,size,params));
    }

    @PutMapping("/updateRMB/{rmb}/{gameId}/")
    public WebResult updateRMB(@PathVariable double rmb,@PathVariable int gameId){
        gameIntegralService.updateRMB(rmb,gameId);
        return WebResult.of();
    }
}
