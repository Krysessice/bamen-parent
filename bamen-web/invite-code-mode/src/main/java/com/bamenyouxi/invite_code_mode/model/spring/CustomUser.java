package com.bamenyouxi.invite_code_mode.model.spring;

import com.bamenyouxi.core.constant.AuthConstant;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SysAgent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * 自定义用户
 * Created by 13477 on 2017/6/26.
 */
public final class CustomUser implements UserDetails {
	private SysAgent sysAgent;

	private CustomUser() {}

	public CustomUser(SysAgent sysAgent) {
		this.sysAgent = sysAgent;
	}

	public SysAgent getSysAgent() {
		return sysAgent;
	}

	public CustomUser setSysAgent(SysAgent sysAgent) {
		this.sysAgent = sysAgent;
		return this;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new HashSet<SimpleGrantedAuthority>() {{
			add(new SimpleGrantedAuthority(AuthConstant.RoleNames.ROLE_USER));
			if (Arrays.asList(AuthConstant.AdminAuthInfo.ADMIN_ACCOUNTS).contains(getUsername()))
				add(new SimpleGrantedAuthority(AuthConstant.RoleNames.ROLE_ADMIN));
		}};
	}

	@Override
	public String getPassword() {
		return sysAgent.getPassword();
	}

	@Override
	public String getUsername() {
		return sysAgent.getGameId().toString();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return sysAgent.getSysFlag();
	}
}
