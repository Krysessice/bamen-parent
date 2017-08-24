package com.bamenyouxi.room_card_mode.model.mysql.room_card_mode;

import com.bamenyouxi.core.impl.model.mysql.BaseEntity;

import java.math.BigDecimal;

/**
 * model for t_pay_order
 * Created by 13477 on 2017/8/23.
 */
public final class PayOrder extends BaseEntity {

	private String orderNo;
	private Long sysAgentId;
	private BigDecimal payPrice;
	private Integer cardNum;

	private PayOrder() {}

	public String getOrderNo() {
		return orderNo;
	}

	public Long getSysAgentId() {
		return sysAgentId;
	}

	public BigDecimal getPayPrice() {
		return payPrice;
	}

	public Integer getCardNum() {
		return cardNum;
	}
}
