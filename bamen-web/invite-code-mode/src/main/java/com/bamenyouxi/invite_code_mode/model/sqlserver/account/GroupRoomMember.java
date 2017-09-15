package com.bamenyouxi.invite_code_mode.model.sqlserver.account;

import com.bamenyouxi.core.impl.model.sqlserver.BaseEntity;

/**
 * model for userAgentmap
 * Created by 13477 on 2017/8/30.
 */
public final class GroupRoomMember extends BaseEntity {

	private Integer groupRoomId;
	private Integer userId;

	private Integer gameId;
	private String nickName;

	private GroupRoomMember() {}

	public static GroupRoomMember of() {
		return new GroupRoomMember();
	}

	public Integer getGroupRoomId() {
		return groupRoomId;
	}

	public Integer getUserId() {
		return userId;
	}

	public Integer getGameId() {
		return gameId;
	}

	public String getNickName() {
		return nickName;
	}

	public GroupRoomMember groupRoomId(Integer val) {
		this.groupRoomId = val;
		return this;
	}
	public GroupRoomMember userId(Integer val) {
		this.userId = val;
		return this;
	}
}
