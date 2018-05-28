package com.bamenyouxi.invite_code_mode.model.sqlserver.treasure;

import com.bamenyouxi.core.impl.model.sqlserver.BaseEntity;

public class GameIntegral  extends BaseEntity {

    private int userid;
    private int integral;
    private double rmb;
    private Integer gameId;
    private Integer userIds;
    private String nickName;

    public int getUserid() {
        return userid;
    }

    public int getIntegral() {
        return integral;
    }

    public double getRmb() {
        return rmb;
    }

    public Integer getGameId() {
        return gameId;
    }

    public String getNickName() {
        return nickName;
    }

    public Integer getUserIds() {
        return userIds;
    }
}
