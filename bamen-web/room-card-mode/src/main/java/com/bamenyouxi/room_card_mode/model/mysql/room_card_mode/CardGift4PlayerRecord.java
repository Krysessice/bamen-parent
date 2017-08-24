package com.bamenyouxi.room_card_mode.model.mysql.room_card_mode;

import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.impl.AbstractCardGiftRecordEntity;

/**
 * model for t_card_gift_for_player_record
 * Created by 13477 on 2017/8/23.
 */
public final class CardGift4PlayerRecord extends AbstractCardGiftRecordEntity {

	private Integer presentee;

	private CardGift4PlayerRecord() {}

	public Integer getPresentee() {
		return presentee;
	}
}
