package com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode;

import com.bamenyouxi.core.impl.model.mysql.BaseEntity;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * model for t_system_info
 * Created by 13477 on 2017/7/17.
 */
public final class SystemInfo extends BaseEntity {

	private String gameName;
	private String title;
	private Integer leastRecruitNum;
	private BigDecimal t1_commission;
	private BigDecimal t2_commission;
	private BigDecimal t3_commission;
	private BigDecimal payOrderPerdayTotalPayPrice;

	private Date latestClearDate; //最近结算时间

	// 若设置值，将 sysFlag = #{_sysFlag} 的记录置为 #{sysFlag}
	private Boolean _sysFlag;

	private SystemInfo() {}

	public String getGameName() {
		return gameName;
	}

	public String getTitle() {
		return title;
	}

	public Integer getLeastRecruitNum() {
		return leastRecruitNum;
	}

	public BigDecimal getT1_commission() {
		return t1_commission;
	}

	public BigDecimal getT2_commission() {
		return t2_commission;
	}

	public BigDecimal getT3_commission() {
		return t3_commission;
	}

	public BigDecimal getPayOrderPerdayTotalPayPrice() {
		return payOrderPerdayTotalPayPrice;
	}

	public Date getLatestClearDate() {
		return latestClearDate;
	}

	public Boolean get_sysFlag() {
		return _sysFlag;
	}

	public SystemInfo setPayOrderPerdayTotalPayPrice(BigDecimal payOrderPerdayTotalPayPrice) {
		this.payOrderPerdayTotalPayPrice = payOrderPerdayTotalPayPrice;
		return this;
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
