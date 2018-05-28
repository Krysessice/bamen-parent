package com.bamenyouxi.invite_code_mode.model.sqlserver.record;

import com.bamenyouxi.core.impl.model.mysql.BaseEntity;

import java.sql.Timestamp;

public class RecordBuildQunRecord extends BaseEntity {

    private Integer id;
    private Integer gameid;
    private String qunName;
    private String nickName;
    private  Timestamp builDate;

    public Integer getQunid() {
        return id;
    }

    public Integer getGameid() {
        return gameid;
    }

    public String getQunName() {
        return qunName;
    }

    public String getNickName() {
        return nickName;
    }

    public Timestamp getBuilDate() {
        return builDate;
    }

    public void setQunid(Integer id) {
        this.id = id;
    }

    public void setGameid(Integer gameid) {
        this.gameid = gameid;
    }

    public void setQunName(String qunName) {
        this.qunName = qunName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setBuilDate(Timestamp builDate) {
        this.builDate = builDate;
    }
}
