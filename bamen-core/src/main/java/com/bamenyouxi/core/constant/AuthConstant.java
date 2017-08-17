package com.bamenyouxi.core.constant;

/**
 * spring security auth info
 * Created by 13477 on 2017/6/21.
 */
public final class AuthConstant {

	/**
	 * 授权sql
	 */
	public static class AuthSqlConstant {
		public static final String GET_AGENT_BY_GAME_ID =
				"select a.F_GAME_ID username, a.F_PASSWORD password, a.F_SYS_FLAG enabled from t_sys_agent a where a.F_GAME_ID = ?";
		public static final String GET_AUTH = "select ?, 'ROLE_" + RoleNames.USER + "'";
	}

	/**
	 * 管理员信息
	 */
	public static class AdminAuthInfo {
		// 内存中管理员账号与密码
		public static final String ADMIN_USERNAME = "admin";
		public static final String ADMIN_PASSWORD = "aq1sw2de";

		// 数据库中管理员账号
		public static final String[] ADMIN_ACCOUNTS = {"10001"};
	}

	/**
	 * 后台代理常量类
	 */
	public static class SysAgentConstant {
		public static final String DEFAULT_PWD = "123456";
	}

	/**
	 * 角色名
	 */
	public static class RoleNames {
		public static final String ADMIN = "ADMIN";
		public static final String STAFF = "STAFF";
		public static final String USER = "USER";
		public static final String GUEST = "GUEST";

		public static final String ROLE_ADMIN = "ROLE_ADMIN";
		public static final String ROLE_STAFF = "ROLE_STAFF";
		public static final String ROLE_USER = "ROLE_USER";
		public static final String ROLE_GUEST = "ROLE_GUEST";
	}
}
