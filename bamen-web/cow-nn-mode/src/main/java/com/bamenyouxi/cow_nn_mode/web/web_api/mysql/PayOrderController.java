package com.bamenyouxi.cow_nn_mode.web.web_api.mysql;

import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.impl.web.AbstractCrudController;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.PayOrder;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.Price;
import com.bamenyouxi.cow_nn_mode.service.mysql.PayOrderSerice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Action;
import java.util.Map;

@RestController
@RequestMapping("/pay")
public class PayOrderController extends AbstractCrudController<PayOrder, Long> {

    @Autowired
    private PayOrderSerice payOrderSerice;

    @Override
    public AbstractCrudService<PayOrder, Long> getService() {
        return payOrderSerice;
    }

    @GetMapping("/sum/")
    public WebResult getPayAgent(
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
            @RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME,defaultValue = ""+SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
            @RequestParam Map<String ,Object> params){
        return WebResult.of(payOrderSerice.getPayAgent(page,size,params));
    }
}
