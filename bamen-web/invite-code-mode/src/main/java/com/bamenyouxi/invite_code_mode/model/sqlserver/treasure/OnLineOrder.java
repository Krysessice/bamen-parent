package com.bamenyouxi.invite_code_mode.model.sqlserver.treasure;

import com.bamenyouxi.core.impl.model.sqlserver.BaseEntity;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * model for QPTreasureDB.dbo.OnLineOrder
 * Created by 13477 on 2017/6/21.
 */
public final class OnLineOrder extends BaseEntity {

	private Integer onlineId;
	private Integer userId;
	private Integer gameId;
	private String accounts;
	private String orderId;
	private BigDecimal orderAmount;
	private Integer cardGold;
	private Timestamp applyDate;

	public Integer getOnlineId() {
		return onlineId;
	}

	public Integer getUserId() {
		return userId;
	}

	public Integer getGameId() {
		return gameId;
	}

	public String getAccounts() {
		return accounts;
	}

	public String getOrderId() {
		return orderId;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public Integer getCardGold() {
		return cardGold;
	}

	public Timestamp getApplyDate() {
		return applyDate;
	}

}
