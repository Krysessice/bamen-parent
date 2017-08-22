package com.bamenyouxi.invite_code_mode.model.sqlserver.treasure;

import com.bamenyouxi.core.impl.model.mysql.BaseEntity;

public class UserStatus extends BaseEntity {



    private Integer userId;
    private Integer kindId;
    private Integer serverId;
    private Integer roomId;

    public Integer getUserId() {
        return userId;
    }

    public Integer getKindId() {
        return kindId;
    }

    public Integer getServerId() {
        return serverId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public UserStatus(){};
}
