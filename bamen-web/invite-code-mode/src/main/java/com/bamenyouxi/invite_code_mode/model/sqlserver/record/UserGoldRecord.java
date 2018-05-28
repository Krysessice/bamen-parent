package com.bamenyouxi.invite_code_mode.model.sqlserver.record;

import com.bamenyouxi.core.impl.model.mysql.BaseEntity;

import java.sql.Timestamp;

public class UserGoldRecord extends BaseEntity {

    private Integer userId;
    private  Integer kindid;
    private  Integer gold;
    private Integer gameleve;
    private Integer systemcost;
    private Integer gameType;
    private Timestamp inserttime;
    private  Integer costId;
    private  Integer gameId;

    public Integer getCostId() {
        return costId;
    }

    public Integer getUserId() {
        return userId;
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

    public Integer getSystemcost() {
        return systemcost;
    }

    public Integer getGameType() {
        return gameType;
    }

    public Timestamp getInserttime() {
        return inserttime;
    }

    public Integer getGameId() {
        return gameId;
    }
}
