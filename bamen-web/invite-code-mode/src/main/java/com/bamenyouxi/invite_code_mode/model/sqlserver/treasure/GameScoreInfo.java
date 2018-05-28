package com.bamenyouxi.invite_code_mode.model.sqlserver.treasure;

import com.bamenyouxi.core.impl.model.sqlserver.BaseEntity;

/**
 * model for QPTreasureDB.dbo.GameScoreInfo
 * Created by 13477 on 2017/8/8.
 */
public final class GameScoreInfo extends BaseEntity {

	private Integer userId;
	private Long insureScore;
	private Integer score;

	private Integer gameId;

	public static GameScoreInfo ofUserId(Integer userId, Long insureScore) {
		return new GameScoreInfo(userId, null, insureScore);
	}

	public static GameScoreInfo ofGameId(Integer gameId, Long insureScore) {
		return new GameScoreInfo(null, gameId, insureScore);
	}

	public static GameScoreInfo ofGameId(Integer gameId, int score) {
		return new GameScoreInfo(null, gameId, (long) score);
	}

	private GameScoreInfo() {}

	private GameScoreInfo(Integer userId, Integer gameId, Long insureScore) {
		this.userId = userId;
		this.gameId = gameId;
		this.insureScore = insureScore;
		this.score=score;
	}

	public Integer getUserId() {
		return userId;
	}

	public Long getInsureScore() {
		return insureScore;
	}

	public Integer getScore() {
		return score;
	}

	public Integer getGameId() {
		return gameId;
	}
}
