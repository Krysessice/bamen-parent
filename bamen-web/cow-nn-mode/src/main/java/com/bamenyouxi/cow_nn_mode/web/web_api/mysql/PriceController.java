package com.bamenyouxi.cow_nn_mode.web.web_api.mysql;

import com.alibaba.fastjson.JSONObject;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.impl.web.AbstractCrudController;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.Price;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.Role;
import com.bamenyouxi.cow_nn_mode.service.mysql.PriceService;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/Price")
public class PriceController extends AbstractCrudController<Price, Long> {
    @Autowired
    private PriceService priceService;

    @Override
    public AbstractCrudService<Price, Long> getService() {
        return priceService;
    }

    @GetMapping("/getPrice/")
    public WebResult getPrice(
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
            Map<String,Object> params){
        return WebResult.of(priceService.getPrice(page,size,params));
    }

    @GetMapping("/payZs/{payZs}/")
    @JsonSerialize
    public @ResponseBody
    JSONObject payZs(HttpServletRequest request, HttpServletResponse response, @PathVariable String payZs) throws Exception {
        return priceService.payZs(request,response,payZs);
    }


    @RequestMapping(value="/notify", method = RequestMethod.POST)
   /* @GetMapping("/notify")*/
    public void notify_params(HttpServletRequest request, HttpServletResponse response) throws Exception {
            priceService.weixin_notify_url(request,response);
    }
}
