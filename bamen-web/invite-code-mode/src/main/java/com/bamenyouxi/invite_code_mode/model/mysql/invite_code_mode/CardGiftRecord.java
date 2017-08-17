package com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode;

import com.bamenyouxi.core.impl.model.mysql.BaseEntity;

/**
 * model for t_card_gift_record
 * Created by 13477 on 2017/8/8.
 */
public final class CardGiftRecord extends BaseEntity {

	private Integer presenter;
	private Integer presentee;
	private Integer cardNum;
	private String giftReason;

	public static CardGiftRecord of(Integer presenter, Integer presentee, Integer cardNum) {
		return of(presenter, presentee, cardNum, null);
	}

	public static CardGiftRecord of(Integer presenter, Integer presentee, Integer cardNum, String giftReason) {
		return new CardGiftRecord(presenter, presentee, cardNum, giftReason);
	}

	private CardGiftRecord() {}

	private CardGiftRecord(Integer presenter, Integer presentee, Integer cardNum, String giftReason) {
		this.presenter = presenter;
		this.presentee = presentee;
		this.cardNum = cardNum;
		this.giftReason = giftReason;
	}

	public Integer getPresenter() {
		return presenter;
	}

	public Integer getPresentee() {
		return presentee;
	}

	public Integer getCardNum() {
		return cardNum;
	}

	public String getGiftReason() {
		return giftReason;
	}
}
