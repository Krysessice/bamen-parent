package com.bamenyouxi.cow_nn_mode.model.Spring;

import com.bamenyouxi.core.constant.AuthConstant;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.SysAgent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

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
		return sysAgent.getAccount();
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
		return true;
	}
}
