package com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode;

import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.impl.AbstractOpenRoomRecordEntity;

import java.sql.Timestamp;

/**
 * model for t_user_open_room_perday_record
 * Created by 13477 on 2017/7/24.
 */
public final class UserOpenRoomPerdayRecord extends AbstractOpenRoomRecordEntity {


	protected Integer userId;
	private Integer lastLoginTime;
	protected Integer gameId;
	protected String accounts;

	public Integer getLastLoginTime() {
		return lastLoginTime;
	}

	public Integer getUserId() {
		return userId;
	}

	public Integer getGameId() {
		return gameId;
	}

	public String getAccounts() {
		return accounts;
	}
}
