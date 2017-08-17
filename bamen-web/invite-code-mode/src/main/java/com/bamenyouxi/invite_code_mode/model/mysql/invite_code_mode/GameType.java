package com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode;

import com.bamenyouxi.core.impl.model.mysql.BaseEntity;

/**
 * model for t_game_type
 * Created by 13477 on 2017/8/2.
 */
public final class GameType extends BaseEntity {

	private Integer gameTypeId;
	private String gameName;

	public Integer getGameTypeId() {
		return gameTypeId;
	}

	public String getGameName() {
		return gameName;
	}
}
