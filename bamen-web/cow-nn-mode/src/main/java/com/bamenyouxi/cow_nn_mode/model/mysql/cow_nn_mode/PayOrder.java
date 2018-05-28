package com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode;

import com.bamenyouxi.core.impl.model.mysql.BaseEntity;

import java.math.BigDecimal;

public class PayOrder extends BaseEntity {

    private String account;
    private String nickName;
    private Integer sysAgentRoleId;
    private String orderNo;
    private String sysAgentId;
    private BigDecimal payPrice;
    private Integer cardNum;

    public String getNickName() {
        return nickName;
    }

    public String getAccount() {
        return account;
    }

    public Integer getSysAgentRoleId() {
        return sysAgentRoleId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public String getSysAgentId() {
        return sysAgentId;
    }

    public BigDecimal getPayPrice() {
        return payPrice;
    }

    public Integer getCardNum() {
        return cardNum;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setSysAgentRoleId(Integer sysAgentRoleId) {
        this.sysAgentRoleId = sysAgentRoleId;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public void setSysAgentId(String sysAgentId) {
        this.sysAgentId = sysAgentId;
    }

    public void setPayPrice(BigDecimal payPrice) {
        this.payPrice = payPrice;
    }

    public void setCardNum(Integer cardNum) {
        this.cardNum = cardNum;
    }
}
