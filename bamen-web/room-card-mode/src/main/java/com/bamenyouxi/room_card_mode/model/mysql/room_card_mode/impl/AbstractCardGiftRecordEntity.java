package com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.impl;

import com.bamenyouxi.core.impl.model.mysql.BaseEntity;

/**
 * 房卡赠送记录抽象
 * Created by 13477 on 2017/8/23.
 */
public abstract class AbstractCardGiftRecordEntity extends BaseEntity {

	private Long presenter;
	private Integer cardNum;
	private String giftReason;

	public void setCardNum(Integer cardNum) {
		this.cardNum = cardNum;
	}

	public void setPresenter(Long presenter) {
		this.presenter = presenter;
	}

	public Long getPresenter() {
		return presenter;
	}

	public Integer getCardNum() {
		return cardNum;
	}

	public String getGiftReason() {
		return giftReason;
	}
}
