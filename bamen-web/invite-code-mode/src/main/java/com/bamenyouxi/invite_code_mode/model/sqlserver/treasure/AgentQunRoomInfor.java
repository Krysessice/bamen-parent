package com.bamenyouxi.invite_code_mode.model.sqlserver.treasure;

import com.bamenyouxi.core.impl.model.mysql.BaseEntity;

public class AgentQunRoomInfor extends BaseEntity {

    private  Integer roomNum;
    private  Integer userID;
    private  Integer kindID;
    private  Integer gameRule;
    private  Integer gameType;
    private  Integer gameStaus;
    private  Integer playNum;
    private  Integer playCounts;
    private  Integer gamePlayCount;
    private  Integer fangKaCost;
    private  Integer qunID;
    private  Integer roomType;

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getRoomNum() {
        return roomNum;
    }

    public Integer getUserID() {
        return userID;
    }

    public Integer getKindID() {
        return kindID;
    }

    public Integer getGameRule() {
        return gameRule;
    }

    public Integer getGameType() {
        return gameType;
    }

    public Integer getGameStaus() {
        return gameStaus;
    }

    public Integer getPlayNum() {
        return playNum;
    }

    public Integer getPlayCounts() {
        return playCounts;
    }

    public Integer getGamePlayCount() {
        return gamePlayCount;
    }

    public Integer getFangKaCost() {
        return fangKaCost;
    }

    public Integer getQunID() {
        return qunID;
    }

    public Integer getRoomType() {
        return roomType;
    }
}
