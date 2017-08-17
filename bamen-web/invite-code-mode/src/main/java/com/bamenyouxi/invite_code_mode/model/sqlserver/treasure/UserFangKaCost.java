package com.bamenyouxi.invite_code_mode.model.sqlserver.treasure;

import com.bamenyouxi.core.impl.model.sqlserver.BaseEntity;

import java.sql.Timestamp;

/**
 * model for QPTreasureDB.dbo.UserFangKaCost
 * Created by 13477 on 2017/7/24.
 */
public final class UserFangKaCost extends BaseEntity {

	private Integer userId;
	private Integer kindId;
	private Integer fangKa;
	private Timestamp costTime;

	private UserFangKaCost() {}

	public Integer getUserId() {
		return userId;
	}

	public Integer getKindId() {
		return kindId;
	}

	public Integer getFangKa() {
		return fangKa;
	}

	public Timestamp getCostTime() {
		return costTime;
	}
}
