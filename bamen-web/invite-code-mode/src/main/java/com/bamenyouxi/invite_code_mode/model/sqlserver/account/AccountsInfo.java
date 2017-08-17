package com.bamenyouxi.invite_code_mode.model.sqlserver.account;

import com.bamenyouxi.core.impl.model.sqlserver.BaseEntity;

import java.sql.Timestamp;

/**
 * model for AccountsInfo
 * Created by 13477 on 2017/6/20.
 */
public final class AccountsInfo extends BaseEntity {

	private Integer userId;
	private Integer gameId;
	private String accounts;
	private String nickName;
	private Integer agentLevel;     //代理级别，这里表示为是否为代理的标识
	private Integer playingGame;    //上级代理gameId
	private Timestamp registerDate;
	private Timestamp superAgentTime;

	private Long insureScore;

	private Integer _playingGame;

	private AccountsInfo() {}

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

	public Integer getAgentLevel() {
		return agentLevel;
	}

	public Integer getPlayingGame() {
		return playingGame;
	}

	public Timestamp getRegisterDate() {
		return registerDate;
	}

	public Timestamp getSuperAgentTime() {
		return superAgentTime;
	}

	public Long getInsureScore() {
		return insureScore;
	}

	public AccountsInfo(Builder builder) {
		this.gameId = builder.gameId;
		this.agentLevel = builder.agentLevel;
		this.playingGame = builder.playingGame;
		this._playingGame = builder._playingGame;
	}

	public static class Builder {
		private Integer gameId;
		private Integer agentLevel;
		private Integer playingGame;
		private Integer _playingGame;

		public Builder gameId(Integer val) {
			this.gameId = val;
			return this;
		}

		public Builder agentLevel(Integer val) {
			this.agentLevel = val;
			return this;
		}

		public Builder playingGame(Integer val) {
			this.playingGame = val;
			return this;
		}

		public Builder _playingGame(Integer val) {
			this._playingGame = val;
			return this;
		}

		public AccountsInfo build() {
			return new AccountsInfo(this);
		}
	}
}
