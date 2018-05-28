package com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode;

import com.bamenyouxi.core.impl.model.mysql.BaseEntity;

import java.math.BigDecimal;
import java.sql.Date;

public final class SystemInfo extends BaseEntity{

    private String gameName;
    private String title;
    private Boolean _sysFlag;

    private BigDecimal ratelv1;
    private BigDecimal ratelv2;
    private BigDecimal ratelv3;
    private BigDecimal ratelv4;

    private Date latestClearDate; //最近结算时间

    public BigDecimal getRatelv3() {
        return ratelv3;
    }

    public BigDecimal getRatelv4() {
        return ratelv4;
    }

    public BigDecimal getRatelv1() {
        return ratelv1;
    }

    public BigDecimal getRatelv2() {
        return ratelv2;
    }

    public SystemInfo() {}

    public String getGameName() {
        return gameName;
    }

    public String getTitle() {
        return title;
    }

    public Boolean get_sysFlag() {
        return _sysFlag;
    }

    public Date getLatestClearDate() {
        return latestClearDate;
    }

    public SystemInfo setLatestClearDate(Date latestClearDate) {
        this.latestClearDate = latestClearDate;
        return this;
    }


    private SystemInfo(Builder builder) {
        this.sysFlag = builder.sysFlag;
        this._sysFlag = builder._sysFlag;
    }

    public static class Builder {
        private Boolean sysFlag;
        private Boolean _sysFlag;

        public Builder sysFlag(Boolean val) {
            sysFlag = val;
            return this;
        }

        public Builder _sysFlag(Boolean val) {
            _sysFlag = val;
            return this;
        }

        public SystemInfo build() {
            return new SystemInfo(this);
        }
    }

}
