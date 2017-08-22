package com.bamenyouxi.invite_code_mode.model.sqlserver.treasure;

import com.bamenyouxi.core.impl.model.mysql.BaseEntity;

/**
 * model for QPTreasureDB.dbo.UserStatus
 */
public final class UserStatus extends BaseEntity {

	private Integer userId;
	private Integer kindId;
	private Integer roomId;

	public static UserStatus of(Integer userId) {
		return new UserStatus(userId);
	}

	private UserStatus() {}

	private UserStatus(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserId() {
		return userId;
	}

	public Integer getKindId() {
		return kindId;
	}

	public Integer getRoomId() {
		return roomId;
	}
}
