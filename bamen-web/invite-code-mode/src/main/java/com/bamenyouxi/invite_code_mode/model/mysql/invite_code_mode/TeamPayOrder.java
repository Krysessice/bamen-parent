package com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 团队充值统计
 * Created by 13477 on 2017/7/11.
 */
public final class TeamPayOrder implements Serializable {

	private String teamNo; //t1, t2, t3
	private String teamName;
	private BigDecimal payPrice;
	private Long cardGold;
	private BigDecimal commission; //提成

	public String getTeamNo() {
		return teamNo;
	}

	public String getTeamName() {
		return teamName;
	}

	public BigDecimal getPayPrice() {
		return payPrice;
	}

	public Long getCardGold() {
		return cardGold;
	}

	public BigDecimal getCommission() {
		return commission;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
