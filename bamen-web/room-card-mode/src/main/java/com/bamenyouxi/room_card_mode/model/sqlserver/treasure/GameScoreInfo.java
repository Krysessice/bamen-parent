package com.bamenyouxi.room_card_mode.model.sqlserver.treasure;

import com.bamenyouxi.core.impl.model.sqlserver.BaseEntity;

/**
 * model for QPTreasureDB.dbo.GameScoreInfo
 * Created by 13477 on 2017/8/8.
 */
public final class GameScoreInfo extends BaseEntity {

	private Integer userId;
	private Long insureScore;

	private Integer gameId;

	public static GameScoreInfo ofUserId(Integer userId, Long insureScore) {
		return new GameScoreInfo(userId, null, insureScore);
	}

	public static GameScoreInfo ofGameId(Integer gameId, Long insureScore) {
		return new GameScoreInfo(null, gameId, insureScore);
	}

	private GameScoreInfo() {}

	private GameScoreInfo(Integer userId, Integer gameId, Long insureScore) {
		this.userId = userId;
		this.gameId = gameId;
		this.insureScore = insureScore;
	}

	public Integer getUserId() {
		return userId;
	}

	public Long getInsureScore() {
		return insureScore;
	}

	public GameScoreInfo(Builder builder) {
		this.userId = builder.userId;
		this.insureScore=builder.insureScore;
	}

	public static class Builder {
		private Integer userId;
		private Long insureScore;


		public Builder userId(Integer val) {
			this.userId = val;
			return this;
		}

		public Builder insureScore(Long val) {
			this.insureScore = val;
			return this;
		}



		public GameScoreInfo build() {
			return new GameScoreInfo(this);
		}


	}
}
