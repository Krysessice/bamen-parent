package com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode;


import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.impl.AbstractCardGiftRecordEntity;

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
