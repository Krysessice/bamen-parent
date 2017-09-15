package com.bamenyouxi.room_card_mode.util;

import com.bamenyouxi.core.constant.AuthConstant;
import com.bamenyouxi.core.constant.RedisConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.SysAgent;
import com.bamenyouxi.room_card_mode.model.spring.CustomUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * 登录用户工具类
 * Created by 13477 on 2017/6/23.
 */
public final class UserDetailsUtil {
	private static UserDetails userDetails;

	private static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	private static UserDetails getUserDetails() {
		return userDetails = (UserDetails) getAuthentication().getPrincipal();
	}

	private static CustomUser getCustomUser() {
		getUserDetails();
		Assert.isTrue(userDetails instanceof CustomUser, TipMsgConstant.SYS_AGENT_INFO_INVALID);
		return (CustomUser) userDetails;
	}

	private static SysAgent getSysAgent() {
		return getCustomUser().getSysAgent();
	}

	/**
	 * 刷新用户信息
	 * @param sysAgent 代理最新信息
	 */
	public static void refresh(SysAgent sysAgent) {
		CustomUser customUser = getCustomUser().setSysAgent(sysAgent);
		UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
				customUser, getAuthentication().getCredentials(), getUserDetails().getAuthorities());
		result.setDetails(getAuthentication().getDetails());
		SecurityContextHolder.getContext().setAuthentication(result);
	}

	public static String getUserName() {
		return getUserDetails().getUsername();
	}

	public static String getAccoutnt() {
		return getSysAgent().getAccount();
	}

	public static String getPassword() {
		String pwd = getUserDetails().getPassword();
		Assert.isTrue(!StringUtils.isEmpty(pwd) && pwd.equals(getSysAgent().getPassword()), TipMsgConstant.OPERATION_EXCEPTION_AND_LOGOUT);
		return pwd;
	}

	public static Long getId() {
		return getSysAgent().getId();
	}

	public static boolean isAdmin() {
		return (getUserDetails() != null) && AuthorityUtils.authorityListToSet(getUserDetails().getAuthorities()).contains(AuthConstant.RoleNames.ROLE_ADMIN);
	}

	/* redis */
	public static String getRedisKey_Salt() {
		return getRedisKey_Salt(UserDetailsUtil.getUserName());
	}

	public static String getRedisKey_Salt(String userName) {
		return getRedisKey(RedisConstant.SALT_PREFIX, userName);
	}

	private static String getRedisKey(String prefix, String key) {
		return prefix + key;
	}
}
