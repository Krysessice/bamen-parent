package com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.impl;

import java.math.BigDecimal;

/**
 * 订单通用属性抽象
 * Created by 13477 on 2017/6/22.
 */
public abstract class AbstractPayOrderRefEntity extends AbstractUserInfoEntity {

	protected BigDecimal payPrice;
	protected Integer cardGold;
	private BigDecimal systemCost;

	protected String nickName;

	public BigDecimal getPayPrice() {
		return payPrice;
	}

	public BigDecimal getSystemCost() {
		return systemCost;
	}

	public Integer getCardGold() {
		return cardGold;
	}

	public String getNickName() {
		return nickName;
	}
}
