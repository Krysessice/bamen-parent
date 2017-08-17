package com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode;

import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.impl.AbstractPayOrderRefEntity;

/**
 * model for t_pay_order_perday_statistic
 * Created by 13477 on 2017/6/22.
 */
public final class PayOrderPerdayStatistic extends AbstractPayOrderRefEntity {

	private PayOrderPerdayStatistic() {}

	private PayOrderPerdayStatistic(Builder builder) {
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

		public PayOrderPerdayStatistic build() {
			return new PayOrderPerdayStatistic(this);
		}
	}
}
