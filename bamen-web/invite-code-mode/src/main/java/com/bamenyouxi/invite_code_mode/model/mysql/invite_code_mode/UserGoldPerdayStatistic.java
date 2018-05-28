package com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode;

import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.impl.AbstractPayOrderRefEntity;

/**
 * model for t_user_gold_perday_statistic
 * Created by 13477 on 2018/4/26.
 */
public final class UserGoldPerdayStatistic extends AbstractPayOrderRefEntity {

	private UserGoldPerdayStatistic() {}

	private UserGoldPerdayStatistic(Builder builder) {
		this.gameId = builder.gameId;

		this._gameId = builder._gameId;
	}

	public static class Builder {
		private Integer gameId;

		private Integer _gameId;

		public Builder gameId(Integer val) {
			gameId = val;
			return this;
		}

		public Builder _gameId(Integer val) {
			_gameId = val;
			return this;
		}

		public UserGoldPerdayStatistic build() {
			return new UserGoldPerdayStatistic(this);
		}
	}
}
