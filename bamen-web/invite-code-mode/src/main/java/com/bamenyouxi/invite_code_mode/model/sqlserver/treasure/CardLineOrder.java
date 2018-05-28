package com.bamenyouxi.invite_code_mode.model.sqlserver.treasure;

import com.bamenyouxi.core.constant.SysConstant;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class CardLineOrder {

    private int userid;
    private int gameId;
    private String account;
    private String orderId;
    private int priceCard;
    private int payCard;
    @JsonFormat(locale = SysConstant.JsonFormat.DEFAULT_LOCAL, timezone = SysConstant.JsonFormat.DEFAULT_TIMEZONE, pattern = SysConstant.JsonFormat.DEFAULT_PATTERN)
    private Timestamp createTime;

    public int getUserid() {
        return userid;
    }

    public int getGameId() {
        return gameId;
    }

    public String getAccount() {
        return account;
    }

    public String getOrderId() {
        return orderId;
    }

    public int getPriceCard() {
        return priceCard;
    }

    public int getpayCard() {
        return payCard;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }
}
