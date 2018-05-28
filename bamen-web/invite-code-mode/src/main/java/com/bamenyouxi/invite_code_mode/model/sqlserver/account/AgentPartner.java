package com.bamenyouxi.invite_code_mode.model.sqlserver.account;

import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.impl.model.mysql.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class AgentPartner extends BaseEntity {

    private Integer partnerGameid;
    private Integer qunid;
    private String qunName;
    @JsonFormat(locale = SysConstant.JsonFormat.DEFAULT_LOCAL, timezone = SysConstant.JsonFormat.DEFAULT_TIMEZONE, pattern = SysConstant.JsonFormat.DEFAULT_PATTERN)
    private Timestamp createTime;

    public Integer getPartnerGameid() {
        return partnerGameid;
    }

    @Override
    public Timestamp getCreateTime() {
        return createTime;
    }

    public String getQunName() {
        return qunName;
    }

    public Integer getQunid() {
        return qunid;
    }
}
