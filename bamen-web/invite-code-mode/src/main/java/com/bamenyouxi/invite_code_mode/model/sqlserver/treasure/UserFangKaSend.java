package com.bamenyouxi.invite_code_mode.model.sqlserver.treasure;

import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.impl.model.sqlserver.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * model for QPTreasureDB.dbo.UserFangKaSend
 * Created by 13477 on 2017/8/21.
 */
public final class UserFangKaSend extends BaseEntity {

	private Integer sendGameId;
	private String sendNickName;
	private Integer receiveGameId;
	private String receiveNickName;
	@JsonFormat(locale = SysConstant.JsonFormat.DEFAULT_LOCAL, timezone = SysConstant.JsonFormat.DEFAULT_TIMEZONE, pattern = SysConstant.JsonFormat.DEFAULT_PATTERN)
	private Date sendTime;

	public Integer getSendGameId() {
		return sendGameId;
	}

	public String getSendNickName() {
		return sendNickName;
	}

	public Integer getReceiveGameId() {
		return receiveGameId;
	}

	public String getReceiveNickName() {
		return receiveNickName;
	}

	public Date getSendTime() {
		return sendTime;
	}
}
