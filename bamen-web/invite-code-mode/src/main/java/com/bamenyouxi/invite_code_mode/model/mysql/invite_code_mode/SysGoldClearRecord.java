package com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode;

import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.impl.model.mysql.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * model for t_sys_gold_clear_record
 * Created by 13477 on 2018/4/27.
 */
public final class SysGoldClearRecord extends BaseEntity {

	@JsonFormat(locale = SysConstant.JsonFormat.DEFAULT_LOCAL, timezone = SysConstant.JsonFormat.DEFAULT_TIMEZONE, pattern = SysConstant.JsonFormat.DATE_PATTERN)
	private Date startDate;
	@JsonFormat(locale = SysConstant.JsonFormat.DEFAULT_LOCAL, timezone = SysConstant.JsonFormat.DEFAULT_TIMEZONE, pattern = SysConstant.JsonFormat.DATE_PATTERN)
	private Date endDate;
	private BigDecimal systemCost;
	private BigDecimal clearPrice;

	private SysGoldClearRecord() {}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public BigDecimal getClearPrice() {
		return clearPrice;
	}

	public BigDecimal getSystemCost() {
		return systemCost;
	}

	private SysGoldClearRecord(Builder builder) {
		this.startDate = builder.startDate;
		this.endDate = builder.endDate;
		this.systemCost = builder.systemCost;
		this.clearPrice=builder.clearPrice;
	}

	public static class Builder {
		private Date startDate;
		private Date endDate;
		private BigDecimal systemCost;
		private BigDecimal clearPrice;

		public Builder startDate(Date val) {
			startDate = val;
			return this;
		}
		public Builder endDate(Date val) {
			endDate = val;
			return this;
		}
		public Builder systemCost(BigDecimal val) {
			systemCost = val;
			return this;
		}
		public Builder clearPrice(BigDecimal val) {
			clearPrice = val;
			return this;
		}

		public SysGoldClearRecord build() {
			return new SysGoldClearRecord(this);
		}
	}
}
