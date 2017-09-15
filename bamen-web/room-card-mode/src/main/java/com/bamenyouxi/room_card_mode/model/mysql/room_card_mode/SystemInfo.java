package com.bamenyouxi.room_card_mode.model.mysql.room_card_mode;

import com.bamenyouxi.core.impl.model.mysql.BaseEntity;

import java.math.BigDecimal;

public final class SystemInfo extends BaseEntity{

    private String gameName;
    private String title;
    private Boolean _sysFlag;

    private BigDecimal ratelv1;
    private BigDecimal ratelv2;

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
