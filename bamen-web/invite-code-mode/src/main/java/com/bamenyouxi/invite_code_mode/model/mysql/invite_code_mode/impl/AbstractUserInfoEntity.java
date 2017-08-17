package com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.impl;

import com.bamenyouxi.core.impl.model.mysql.BaseEntity;

/**
 * 用户通用属性抽象
 * Created by 13477 on 2017/6/22.
 */
public abstract class AbstractUserInfoEntity extends BaseEntity {

	protected Integer userId;
	protected Integer gameId;
	protected String accounts;
	protected String nickName;

	protected Integer _gameId;

	public Integer getUserId() {
		return userId;
	}

	public Integer getGameId() {
		return gameId;
	}

	public String getAccounts() {
		return accounts;
	}

	public String getNickName() {
		return nickName;
	}
}
