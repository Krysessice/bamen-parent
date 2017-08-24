package com.bamenyouxi.room_card_mode.model.mysql.room_card_mode;

import com.bamenyouxi.core.impl.model.mysql.BaseEntity;

/**
 * model for t_sys_agent_extend
 * Created by 13477 on 2017/8/23.
 */
public final class SysAgentExtend extends BaseEntity {

	private Long sysAgentId;
	private Integer leftCardNum;
	private Integer soldCardNum;

	private SysAgentExtend() {}

	public Long getSysAgentId() {
		return sysAgentId;
	}

	public Integer getLeftCardNum() {
		return leftCardNum;
	}

	public Integer getSoldCardNum() {
		return soldCardNum;
	}

	private SysAgentExtend(Builder builder) {
		this.sysAgentId = builder.sysAgentId;
		this.leftCardNum = builder.leftCardNum;
		this.soldCardNum = builder.soldCardNum;
	}

	public static class Builder {

		private Long sysAgentId;
		private Integer leftCardNum;
		private Integer soldCardNum;

		public Builder sysAgentId(Long val) {
			this.sysAgentId = val;
			return this;
		}
		public Builder leftCardNum(Integer val) {
			this.leftCardNum = val;
			return this;
		}
		public Builder soldCardNum(Integer val) {
			this.soldCardNum = val;
			return this;
		}

		public SysAgentExtend build() {
			return new SysAgentExtend(this);
		}
	}
}
