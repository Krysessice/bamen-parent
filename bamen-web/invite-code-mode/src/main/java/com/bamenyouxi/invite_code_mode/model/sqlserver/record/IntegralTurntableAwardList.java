package com.bamenyouxi.invite_code_mode.model.sqlserver.record;

import com.bamenyouxi.core.impl.model.mysql.BaseEntity;

public class IntegralTurntableAwardList extends BaseEntity {


    private  Integer turnawardno;
    private  Integer turnawardtype;
    private  double turnawardamount;
    private  Integer turnawardgoodamcout;
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

    public Integer getTurnawardgoodamcout() {
        return turnawardgoodamcout;
    }

    public String getTurnawardinstruction() {
        return turnawardinstruction;
    }
}
