package com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode;

import com.bamenyouxi.core.impl.model.mysql.BaseEntity;

import java.math.BigDecimal;

public class Rebate extends BaseEntity {

    private String orderNo;
    private String account;
    private String nickName;
    private BigDecimal payPrice;
    private BigDecimal rebateRate;
    private BigDecimal rebateMoney;
    private String favorerAccount;
    private Integer favorerRoleId;

    public String getAccount() {
        return account;
    }

    public String getNickName() {
        return nickName;
    }

    public BigDecimal getPayPrice() {
        return payPrice;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public BigDecimal getRebateRate() {
        return rebateRate;
    }

    public BigDecimal getRebateMoney() {
        return rebateMoney;
    }

    public String getFavorerAccount() {
        return favorerAccount;
    }

    public Integer getFavorerRoleId() {
        return favorerRoleId;
    }
}
