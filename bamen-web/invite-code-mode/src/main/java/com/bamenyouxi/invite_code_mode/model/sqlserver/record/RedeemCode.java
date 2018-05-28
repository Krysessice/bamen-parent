package com.bamenyouxi.invite_code_mode.model.sqlserver.record;

import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.impl.model.sqlserver.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class RedeemCode extends BaseEntity {

    private int id;
    private String code;
    private int card;
    @JsonFormat(locale = SysConstant.JsonFormat.DEFAULT_LOCAL, timezone = SysConstant.JsonFormat.DEFAULT_TIMEZONE, pattern = SysConstant.JsonFormat.DEFAULT_PATTERN)
    private Timestamp createtime;
    @JsonFormat(locale = SysConstant.JsonFormat.DEFAULT_LOCAL, timezone = SysConstant.JsonFormat.DEFAULT_TIMEZONE, pattern = SysConstant.JsonFormat.DEFAULT_PATTERN)
    private Timestamp usetime;
    @JsonFormat(locale = SysConstant.JsonFormat.DEFAULT_LOCAL, timezone = SysConstant.JsonFormat.DEFAULT_TIMEZONE, pattern = SysConstant.JsonFormat.DEFAULT_PATTERN)
    private Timestamp endtime;


    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public int getCard() {
        return card;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public Timestamp getUsetime() {
        return usetime;
    }

    public Timestamp getEndtime() {
        return endtime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCard(int card) {
        this.card = card;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public void setUsetime(Timestamp usetime) {
        this.usetime = usetime;
    }

    public void setEndtime(Timestamp endtime) {
        this.endtime = endtime;
    }
}
