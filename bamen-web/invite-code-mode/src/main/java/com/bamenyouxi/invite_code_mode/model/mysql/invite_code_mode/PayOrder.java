package com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode;

import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.impl.AbstractPayOrderRefEntity;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * model for t_pay_order
 * Created by 13477 on 2017/6/21.
 */
public final class PayOrder extends AbstractPayOrderRefEntity {

	private String orderNo;

	private PayOrder() {}

	public String getOrderNo() {
		return orderNo;
	}

	private PayOrder(Builder builder) {
		this.id = builder.id;
		this.orderNo = builder.orderNo;
		this.userId = builder.userId;
		this.gameId = builder.gameId;
		this.accounts = builder.accounts;
		this.payPrice = builder.payPrice;
		this.cardGold = builder.cardGold;
		this.createTime = builder.createTime;

		this._gameId = builder._gameId;
	}

	public static class Builder {
		private Long id;
		private String orderNo;
		private Integer userId;
		private Integer gameId;
		private String accounts;
		private BigDecimal payPrice;
		private Integer cardGold;
		private Timestamp createTime;

		private Integer _gameId;

		public Builder id(Long val) {
			id = val;
			return this;
		}
		public Builder orderNo(String val) {
			orderNo = val;
			return this;
		}
		public Builder userId(Integer val) {
			userId = val;
			return this;
		}
		public Builder gameId(Integer val) {
			gameId = val;
			return this;
		}
		public Builder accounts(String val) {
			accounts = val;
			return this;
		}
		public Builder payPrice(BigDecimal val) {
			payPrice = val;
			return this;
		}
		public Builder cardGold(Integer val) {
			cardGold = val;
			return this;
		}
		public Builder createTime(Timestamp val) {
			createTime = val;
			return this;
		}

		public Builder _gameId(Integer val) {
			_gameId = val;
			return this;
		}

		public PayOrder build() {
			return new PayOrder(this);
		}
	}
}
