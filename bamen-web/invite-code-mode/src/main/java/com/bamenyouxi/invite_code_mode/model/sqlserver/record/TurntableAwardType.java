package com.bamenyouxi.invite_code_mode.model.sqlserver.record;

import com.bamenyouxi.core.impl.model.mysql.BaseEntity;

public final class TurntableAwardType  extends BaseEntity {


    private Integer turnawardtype;
    private String turnawardinstruction;

    public Integer getTurnAwardType() {
        return turnawardtype;
    }

    public String getTurnAwardInstruction() {
        return turnawardinstruction;
    }
}
