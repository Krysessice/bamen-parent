package com.bamenyouxi.core.impl.model.mysql;

import com.alibaba.fastjson.JSON;
import com.bamenyouxi.core.constant.SysConstant;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * mysql top model class
 * Created by hc on 2017/7/9.
 */
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    protected Long id;
    protected Boolean sysFlag;
    protected Long creator;
    @JsonFormat(locale = SysConstant.JsonFormat.DEFAULT_LOCAL, timezone = SysConstant.JsonFormat.DEFAULT_TIMEZONE, pattern = SysConstant.JsonFormat.DEFAULT_PATTERN)
    protected Timestamp createTime;
    protected Long modifier;
    protected Timestamp modifyTime;

    public Long getId() {
        return id;
    }

    public Boolean getSysFlag() {
        return sysFlag;
    }

    public Long getCreator() {
        return creator;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public Long getModifier() {
        return modifier;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
