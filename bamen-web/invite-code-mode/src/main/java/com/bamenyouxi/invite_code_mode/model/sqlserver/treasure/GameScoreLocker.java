package com.bamenyouxi.invite_code_mode.model.sqlserver.treasure;


import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.impl.model.sqlserver.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class GameScoreLocker  extends BaseEntity {

    private Integer userId;
    private Integer gameId;
    private String  nickName;
    private String  kindName;
    private Integer roomId;
    @JsonFormat(locale = SysConstant.JsonFormat.DEFAULT_LOCAL, timezone = SysConstant.JsonFormat.DEFAULT_TIMEZONE, pattern = SysConstant.JsonFormat.DEFAULT_PATTERN)
    private Timestamp collectDate;

    public GameScoreLocker() {}

    public Integer getGameId() {
        return gameId;
    }

    public String getNickName() {
        return nickName;
    }

    public String getKindName() {
        return kindName;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public Timestamp getCollectDate() {
        return collectDate;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
