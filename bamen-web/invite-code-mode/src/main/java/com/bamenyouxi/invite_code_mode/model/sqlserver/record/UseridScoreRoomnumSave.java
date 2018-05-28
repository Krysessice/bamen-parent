package com.bamenyouxi.invite_code_mode.model.sqlserver.record;

import com.bamenyouxi.core.impl.model.mysql.BaseEntity;

import java.sql.Timestamp;

public class UseridScoreRoomnumSave extends BaseEntity{

    private Integer gameid;
    private Integer kindid;
    private Integer score;
    private Integer roomid;
    private Integer qunid;
    private Integer parnerid;
    private Integer integral;
    private Timestamp collecttime;
    private String qunName;
    private String nickName;

    public Integer getGameid() {
        return gameid;
    }

    public Integer getKindid() {
        return kindid;
    }

    public Integer getScore() {
        return score;
    }

    public Integer getRoomid() {
        return roomid;
    }

    public Integer getQunid() {
        return qunid;
    }

    public Integer getParnerid() {
        return parnerid;
    }

    public Integer getIntegral() {
        return integral;
    }

    public Timestamp getCollecttime() {
        return collecttime;
    }

    public String getQunName() {
        return qunName;
    }

    public String getNickName() {
        return nickName;
    }
}
