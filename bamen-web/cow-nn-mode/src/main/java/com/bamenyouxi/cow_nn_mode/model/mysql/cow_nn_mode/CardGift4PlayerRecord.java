package com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode;

import com.bamenyouxi.core.impl.model.mysql.BaseEntity;

/**
 * model for t_card_gift_record
 * Created by 13477 on 2017/8/8.
 */
public final class CardGift4PlayerRecord extends BaseEntity {

	private String account;
	private String nickName;
	private Integer cardNum;
	private String presenter;
	private Integer presentee;
	private String giftReason;
	private Integer sum;

	public String getAccount() {
		return account;
	}

	public String getNickName() {
		return nickName;
	}

	public void setSum(Integer sum) {
		this.sum = sum;
	}

	public void setCardNum(Integer cardNum) {
		this.cardNum = cardNum;
	}

	public static CardGift4PlayerRecord of(String presenter, Integer presentee, Integer cardNum) {
		return of(presenter, presentee, cardNum);
	}

	public static CardGift4PlayerRecord of(String presenter, Integer presentee, Integer cardNum,Integer sum) {
		return of(presenter, presentee, cardNum,sum);
	}

	public static CardGift4PlayerRecord of(String presenter, Integer presentee, Integer cardNum, String giftReason) {
		return new CardGift4PlayerRecord(presenter, presentee, cardNum, giftReason);
	}

	public static CardGift4PlayerRecord of(String presenter, Integer presentee, Integer cardNum, String giftReason,Integer sum) {
		return new CardGift4PlayerRecord(presenter, presentee, cardNum, giftReason,sum);
	}

	private CardGift4PlayerRecord() {}

	private CardGift4PlayerRecord(String presenter, Integer presentee, Integer cardNum, String giftReason, Integer sum) {
		this.presenter = presenter;
		this.presentee = presentee;
		this.cardNum = cardNum;
		this.giftReason = giftReason;
		this.sum=sum;
	}

	private CardGift4PlayerRecord(String presenter, Integer presentee, Integer cardNum, String giftReason) {
		this.presenter = presenter;
		this.presentee = presentee;
		this.cardNum = cardNum;
		this.giftReason = giftReason;
	}

	public Integer getSum() {
		return sum;
	}

	public void setPresentee(Integer presentee) {
		this.presentee = presentee;
	}

	public String getPresenter() {
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
