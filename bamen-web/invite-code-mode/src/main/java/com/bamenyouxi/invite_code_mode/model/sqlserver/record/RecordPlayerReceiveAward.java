package com.bamenyouxi.invite_code_mode.model.sqlserver.record;

import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.impl.model.mysql.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class RecordPlayerReceiveAward extends BaseEntity {

    private int recordawardno;
    private int userid;
    @JsonFormat(locale = SysConstant.JsonFormat.DEFAULT_LOCAL, timezone = SysConstant.JsonFormat.DEFAULT_TIMEZONE, pattern = SysConstant.JsonFormat.DEFAULT_PATTERN)
    private Timestamp playerturntime;
    private int recordawardtype;
    private double recordawardamount;
    private Integer gameId;
    private String nickName;

    public Integer getGameId() {
        return gameId;
    }

    public String getNickName() {
        return nickName;
    }

    public int getRecordawardno() {
        return recordawardno;
    }

    public int getUserid() {
        return userid;
    }

    public Timestamp getPlayerturntime() {
        return playerturntime;
    }

    public int getRecordawardtype() {
        return recordawardtype;
    }

    public double getRecordawardamount() {
        return recordawardamount;
    }
}
