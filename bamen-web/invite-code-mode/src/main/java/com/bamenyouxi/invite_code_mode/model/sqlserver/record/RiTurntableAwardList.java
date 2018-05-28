package com.bamenyouxi.invite_code_mode.model.sqlserver.record;

import com.bamenyouxi.core.impl.model.mysql.BaseEntity;

public class RiTurntableAwardList extends BaseEntity {

    private Integer turnawardno;
    private Integer turnawardtype;
    private double turnawardamount;
    private double turnawardprobability;
    private String turnawardinstruction;

    public Integer getTurnawardno() {
        return turnawardno;
    }

    public Integer getTurnawardtype() {
        return turnawardtype;
    }

    public double getTurnawardamount() {
        return turnawardamount;
    }

    public double getTurnawardprobability() {
        return turnawardprobability;
    }

    public String getTurnAwardInstruction() {
        return turnawardinstruction;
    }
}
