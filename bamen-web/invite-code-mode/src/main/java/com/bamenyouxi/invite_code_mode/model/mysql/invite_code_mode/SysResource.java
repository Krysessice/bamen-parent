package com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode;

import com.bamenyouxi.core.impl.model.mysql.BaseEntity;

/**
 * model for t_sys_resource
 * Created by 13477 on 2017/6/26.
 */
public final class SysResource extends BaseEntity {

	private Long userId;
	private Long resourceId;
	private String resourceName;

	private Integer gameId1;
	private Integer gameId2;

	private SysResource() {}

	public Long getUserId() {
		return userId;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public Integer getGameId1() {
		return gameId1;
	}

	public Integer getGameId2() {
		return gameId2;
	}

	private SysResource(Builder builder) {
		this.userId = builder.userId;
		this.resourceId = builder.resourceId;
		this.resourceName = builder.resourceName;
		this.gameId1 = builder.gameId1;
		this.gameId2 = builder.gameId2;
		this.sysFlag = builder.sysFlag;
	}

	public static class Builder {
		private Long userId;
		private Long resourceId;
		private String resourceName;
		private Integer gameId1;
		private Integer gameId2;
		private Boolean sysFlag;

		public Builder userId(Long val) {
			userId = val;
			return this;
		}
		public Builder resourceId(Long val) {
			resourceId = val;
			return this;
		}
		public Builder resourceName(String val) {
			resourceName = val;
			return this;
		}
		public Builder gameId1(Integer val) {
			gameId1 = val;
			return this;
		}
		public Builder gameId2(Integer val) {
			gameId2 = val;
			return this;
		}
		public Builder sysFlag(Boolean val) {
			sysFlag = val;
			return this;
		}

		public static SysResource gameIdSysResource(Integer gameId1, Integer gameId2, String resourceName) {
			return new Builder().gameId1(gameId1).gameId2(gameId2).resourceName(resourceName).build();
		}

		public static SysResource selfSysResource(Long id, String resourceName) {
			return defaultSysResource(id, id, resourceName);
		}

		public static SysResource defaultSysResource(Long userId, Long resourceId, String resourceName) {
			return new Builder().userId(userId).resourceId(resourceId).resourceName(resourceName).build();
		}

		public SysResource build() {
			return new SysResource(this);
		}
	}

}
