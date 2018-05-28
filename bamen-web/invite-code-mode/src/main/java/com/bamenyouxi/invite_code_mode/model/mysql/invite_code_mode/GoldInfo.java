package com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode;

import com.bamenyouxi.core.impl.model.mysql.BaseEntity;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * model for t_gold_info
 * Created by 13477 on 2018/4/25.
 */
public final class GoldInfo extends BaseEntity {

	private BigDecimal t1_commission;
	private BigDecimal t2_commission;
	private BigDecimal t3_commission;
	private BigDecimal userGoldPerdayTotalSystemCost;

	private Date latestClearDate; //最近结算时间

	// 若设置值，将 sysFlag = #{_sysFlag} 的记录置为 #{sysFlag}
	private Boolean _sysFlag;

	public GoldInfo() {}


	public BigDecimal getT1_commission() {
		return t1_commission;
	}

	public BigDecimal getT2_commission() {
		return t2_commission;
	}

	public BigDecimal getT3_commission() {
		return t3_commission;
	}

	public Date getLatestClearDate() {
		return latestClearDate;
	}

	public Boolean get_sysFlag() {
		return _sysFlag;
	}

	public void setT1_commission(BigDecimal t1_commission) {
		this.t1_commission = t1_commission;
	}

	public void setT2_commission(BigDecimal t2_commission) {
		this.t2_commission = t2_commission;
	}

	public void setT3_commission(BigDecimal t3_commission) {
		this.t3_commission = t3_commission;
	}

	public void set_sysFlag(Boolean _sysFlag) {
		this._sysFlag = _sysFlag;
	}

	public GoldInfo setLatestClearDate(Date latestClearDate) {
		this.latestClearDate = latestClearDate;
		return this;
	}

	public GoldInfo setUserGoldPerdayTotalSystemCost(BigDecimal userGoldPerdayTotalSystemCost) {
		this.userGoldPerdayTotalSystemCost = userGoldPerdayTotalSystemCost;
		return this;
	}

	private GoldInfo(Builder builder) {
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

		public GoldInfo build() {
			return new GoldInfo(this);
		}
	}
}
