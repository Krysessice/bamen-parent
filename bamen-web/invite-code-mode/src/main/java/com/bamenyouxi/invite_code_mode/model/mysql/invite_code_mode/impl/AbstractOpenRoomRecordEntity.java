package com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.impl;

import com.bamenyouxi.core.impl.model.mysql.BaseEntity;

/**
 * 开房记录实体抽象
 * Created by 13477 on 2017/8/2.
 */
public abstract class AbstractOpenRoomRecordEntity extends BaseEntity {

	private Integer gameTypeId;
	private Integer openRoomNum;
	private Long cardCost;
	private Integer gameType;
	private Integer gameTypes;

	private String gameName;

	public Integer getGameTypeId() {
		return gameTypeId;
	}

	public Integer getOpenRoomNum() {
		return openRoomNum;
	}

	public Long getCardCost() {
		return cardCost;
	}

	public String getGameName() {
		return gameName;
	}

	public Integer getGameType() {
		return gameType;
	}

	public Integer getGameTypes() {
		return gameTypes;
	}
}
