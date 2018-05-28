package com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode;

import com.bamenyouxi.core.impl.model.mysql.BaseEntity;

/**
 * model for t_card_gift_record
 * Created by 13477 on 2017/8/8.
 */
public final class CardGiftRecord extends BaseEntity {

	private Integer cardNum;
	private String cardPrice;
	private String selected;
	private Integer presenter;
	private Integer presentee;
	private String giftReason;

	public String getSelected() {
		return selected;
	}

	public String getCardPrice() {
		return cardPrice;
	}

	public static CardGiftRecord of(Integer presenter, Integer presentee,String cardPrice, Integer cardNum,String selected) {
		return of(presenter, presentee,cardPrice, cardNum,selected, null);
	}

	public static CardGiftRecord of(Integer presenter, Integer presentee, String cardPrice,Integer cardNum,String selected, String giftReason) {
		return new CardGiftRecord(presenter, presentee, cardPrice,cardNum,selected, giftReason);
	}

	public CardGiftRecord() {}

	private CardGiftRecord(Integer presenter, Integer presentee, String cardPrice,Integer cardNum, String selected,String giftReason) {
		this.presenter = presenter;
		this.presentee = presentee;
		this.cardNum = cardNum;
		this.cardPrice=cardPrice;
		this.selected=selected;
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
