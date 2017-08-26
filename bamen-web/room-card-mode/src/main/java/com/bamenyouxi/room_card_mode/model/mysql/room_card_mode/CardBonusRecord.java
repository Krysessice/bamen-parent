package com.bamenyouxi.room_card_mode.model.mysql.room_card_mode;

import com.bamenyouxi.core.impl.model.mysql.BaseEntity;

/**
 * model for t_card_bonus_record
 * Created by 13477 on 2017/8/23.
 */
public final class CardBonusRecord extends BaseEntity {

	private Long sysAgentId;
	private Long payOrderId;
	private Long firSuperAgentId;   //上级代理主键id
	private Integer firBonus;
	private Long secSuperAgentId;   //上上级代理主键id
	private Integer secBonus;

	private CardBonusRecord() {}

	public Long getSysAgentId() {
		return sysAgentId;
	}

	public Long getPayOrderId() {
		return payOrderId;
	}

	public Long getFirSuperAgentId() {
		return firSuperAgentId;
	}

	public Integer getFirBonus() {
		return firBonus;
	}

	public Long getSecSuperAgentId() {
		return secSuperAgentId;
	}

	public Integer getSecBonus() {
		return secBonus;
	}
}
