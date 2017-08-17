package com.bamenyouxi.invite_code_mode.model.sqlserver.treasure;

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
}
