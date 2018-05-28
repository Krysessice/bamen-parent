package com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode;


import com.bamenyouxi.core.impl.model.mysql.BaseEntity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class UserGold extends BaseEntity {

    private Integer kindid;
    private  Integer gold;
    private  Integer gameleve;
    private  BigDecimal systemcost;
    private Integer gameType;
    private Integer userId;
    private Integer gameId;
    private String teamNo; //t1, t2, t3
    private String teamName;
    private  String nickName;
    private  Integer costId;
    private BigDecimal commission; //提成
//    private Timestamp createTime;


    public Integer getCostId() {
        return costId;
    }

    public String getNickName() {
        return nickName;
    }

    public String getTeamNo() {
        return teamNo;
    }

    public String getTeamName() {
        return teamName;
    }

    @Override
    public Long getId() {
        return id;
    }

    public Integer getKindid() {
        return kindid;
    }

    public Integer getGold() {
        return gold;
    }

    public Integer getGameleve() {
        return gameleve;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getGameId() {
        return gameId;
    }

    public BigDecimal getSystemcost() {
        return systemcost;
    }

    public Integer getGameType() {
        return gameType;
    }

    @Override
    public Timestamp getCreateTime() {
        return createTime;
    }

    public BigDecimal getCommission() {
        return commission;
    }
}
