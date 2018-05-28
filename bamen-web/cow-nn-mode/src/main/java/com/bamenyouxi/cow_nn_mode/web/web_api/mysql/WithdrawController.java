package com.bamenyouxi.cow_nn_mode.web.web_api.mysql;

import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.impl.web.AbstractCrudController;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.SysAgent;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.Withdraw;
import com.bamenyouxi.cow_nn_mode.service.mysql.WithdrawSerice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/withdraw")
public class WithdrawController  extends AbstractCrudController<Withdraw, Long> {

    @Autowired
    private WithdrawSerice withdrawSerice;

    @Override
    public AbstractCrudService<Withdraw, Long> getService() {
        return withdrawSerice;
    }

    @PostMapping("/add/{withdraw}/")
    public WebResult addWithdraw(@PathVariable BigDecimal withdraw){
        withdrawSerice.addWithdraw(withdraw);
        return WebResult.of();
    }



}
