package com.bamenyouxi.invite_code_mode.model.sqlserver.treasure;

import com.bamenyouxi.core.impl.model.mysql.BaseEntity;

public class AgentRoomBuildAutoDate extends BaseEntity {


    private int qunid;
    private int userid;
    private int kindID;
    private int gameType;
    private int gameRule;
    private int playCount;
    private int roomtype;
    private int dwstatus;


    public int getQunid() {
        return qunid;
    }

    public int getUserid() {
        return userid;
    }

    public int getKindID() {
        return kindID;
    }

    public int getGameType() {
        return gameType;
    }

    public int getGameRule() {
        return gameRule;
    }

    public int getPlayCount() {
        return playCount;
    }

    public int getRoomtype() {
        return roomtype;
    }

    public int getDwstatus() {
        return dwstatus;
    }
}
