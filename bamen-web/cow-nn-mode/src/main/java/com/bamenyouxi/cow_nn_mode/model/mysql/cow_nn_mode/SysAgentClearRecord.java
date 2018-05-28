package com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode;

import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.impl.model.mysql.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * model for t_sys_agent_clear_record
 * Created by 13477 on 2017/6/21.
 */
public final class SysAgentClearRecord extends BaseEntity {

	@JsonFormat(locale = SysConstant.JsonFormat.DEFAULT_LOCAL, timezone = SysConstant.JsonFormat.DEFAULT_TIMEZONE, pattern = SysConstant.JsonFormat.DATE_PATTERN)
	private Date startDate;
	@JsonFormat(locale = SysConstant.JsonFormat.DEFAULT_LOCAL, timezone = SysConstant.JsonFormat.DEFAULT_TIMEZONE, pattern = SysConstant.JsonFormat.DATE_PATTERN)
	private Date endDate;
	private BigDecimal payPrice;

	private SysAgentClearRecord() {}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public BigDecimal getPayPrice() {
		return payPrice;
	}


	private SysAgentClearRecord(Builder builder) {
		this.startDate = builder.startDate;
		this.endDate = builder.endDate;
		this.payPrice = builder.payPrice;
	}

	public static class Builder {
		private Date startDate;
		private Date endDate;
		private BigDecimal payPrice;

		public Builder startDate(Date val) {
			startDate = val;
			return this;
		}
		public Builder endDate(Date val) {
			endDate = val;
			return this;
		}
		public Builder payPrice(BigDecimal val) {
			payPrice = val;
			return this;
		}


		public SysAgentClearRecord build() {
			return new SysAgentClearRecord(this);
		}
	}
}
