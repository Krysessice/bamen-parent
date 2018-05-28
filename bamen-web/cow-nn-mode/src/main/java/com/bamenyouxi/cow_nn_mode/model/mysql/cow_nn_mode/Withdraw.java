package com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode;

import com.bamenyouxi.core.impl.model.mysql.BaseEntity;

import java.math.BigDecimal;


public class Withdraw  extends BaseEntity {

    private String account;
    private BigDecimal withdraw;

    public String getAccount() {
        return account;
    }

    public BigDecimal getWithdraw() {
        return withdraw;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setWithdraw(BigDecimal withdraw) {
        this.withdraw = withdraw;
    }
}
