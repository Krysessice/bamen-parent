package com.bamenyouxi.invite_code_mode.model.sqlserver.account;

import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.model.sqlserver.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.util.Assert;

import java.sql.Timestamp;

/**
 * model for userAgentInfor
 * Created by 13477 on 2017/8/30.
 */
public final class GroupRoom extends BaseEntity {

	private Integer id;
	private Integer gameId;
	private String nickName;
	private String groupName;
	private Integer roomStatus;
	private Integer playerNum;
	@JsonFormat(locale = SysConstant.JsonFormat.DEFAULT_LOCAL, timezone = SysConstant.JsonFormat.DEFAULT_TIMEZONE, pattern = SysConstant.JsonFormat.DEFAULT_PATTERN)
	private Timestamp createTime;
	private Timestamp modifyTime;

	private int playerNumInt = 0; //群房间成员人数，1自增，-1自减，0无操作，默认0

	private GroupRoom() {}

	public static GroupRoom of() {
		return new GroupRoom();
	}

	public Integer getId() {
		return id;
	}

	public Integer getGameId() {
		return gameId;
	}

	public String getNickName() {
		return nickName;
	}

	public String getGroupName() {
		return groupName;
	}

	public Integer getRoomStatus() {
		return roomStatus;
	}

	public Integer getPlayerNum() {
		return playerNum;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public int getPlayerNumInt() {
		return playerNumInt;
	}

	public GroupRoom id(Integer val) {
		this.id = val;
		return this;
	}

	public GroupRoom gameId(Integer val) {
		this.gameId = val;
		return this;
	}

	public GroupRoom nickName(String val) {
		this.nickName = val;
		return this;
	}

	public GroupRoom groupName(String val) {
		this.groupName = val;
		return this;
	}

	public GroupRoom roomStatus(Integer val) {
		this.roomStatus = val;
		return this;
	}

	private GroupRoom playerNum(Integer val) {
		this.playerNum = val;
		return this;
	}
	public GroupRoom playerNumInit() {
		return playerNum(0);
	}
	public GroupRoom playerNumPlus() {
		Assert.notNull(this.playerNum, TipMsgConstant.PARAM_INVALID);
		return playerNum(this.playerNum + 1);
	}
	public GroupRoom playerNumMinus() {
		Assert.notNull(this.playerNum, TipMsgConstant.PARAM_INVALID);
		Assert.isTrue(this.playerNum > 0, TipMsgConstant.PARAM_INVALID);
		return playerNum(this.playerNum - 1);
	}

	public GroupRoom playerNumInt(int val) {
		this.playerNumInt = val;
		return this;
	}
}
