package com.bamenyouxi.room_card_mode.model.mysql.room_card_mode;

import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.impl.AbstractCardGiftRecordEntity;

/**
 * model for t_card_gift_for_agent_record
 * Created by 13477 on 2017/8/23.
 */
public final class CardGift4AgentRecord extends AbstractCardGiftRecordEntity {

	private String account;
	private String nickName;
	private Long presentee;

	public String getAccount() {
		return account;
	}

	public String getNickName() {
		return nickName;
	}

	private CardGift4AgentRecord() {}

	public Long getPresentee() {
		return presentee;
	}



}
