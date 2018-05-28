package com.bamenyouxi.invite_code_mode.model.sqlserver.record;

import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.impl.model.mysql.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class RoomParnterData extends BaseEntity {

    private Integer roomid;
    private Integer gameid;
    private Integer qunid;
    private Integer parnterid;
    private String nickName;
    private Integer score;
    private  Timestamp collecttime;

    public Integer getScore() {
        return score;
    }

    public String getNickName() {
        return nickName;
    }

    public Integer getRoomid() {
        return roomid;
    }

    public Integer getGameid() {
        return gameid;
    }

    public Integer getQunid() {
        return qunid;
    }

    public Integer getParnterid() {
        return parnterid;
    }

    public Timestamp getCollecttime() {
        return collecttime;
    }
}
