package com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode;

import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.impl.AbstractUserInfoEntity;


public class CleanGoldRecord extends AbstractUserInfoEntity {


    private Integer agentGameid;
    private Integer playGameid;
    private Integer score;

    public Integer getAgentGameid() {
        return agentGameid;
    }

    public Integer getPlayGameid() {
        return playGameid;
    }

    public Integer getScore() {
        return score;
    }


    public void setAgentGameid(Integer agentGameid) {
        this.agentGameid = agentGameid;
    }

    public void setPlayGameid(Integer playGameid) {
        this.playGameid = playGameid;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

}
