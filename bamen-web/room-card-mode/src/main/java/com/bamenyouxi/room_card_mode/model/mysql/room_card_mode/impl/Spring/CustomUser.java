package com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.impl.Spring;

import com.bamenyouxi.core.constant.AuthConstant;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.SysAgent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public final class CustomUser implements UserDetails{

    private SysAgent sysAgent;

    public CustomUser(SysAgent sysAgent){}

    public SysAgent getSysAgent() {
        return sysAgent;
    }
    public CustomUser setSysAgent(SysAgent sysAgent) {
        this.sysAgent = sysAgent;
        return this;
    }

    /**
     * 登录判断是管理员还是用户
     * 是管理员就给管理员的权限
     * 是用户就给用户的权限
     * @return Collection
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet<SimpleGrantedAuthority>(){{
            add(new SimpleGrantedAuthority(AuthConstant.RoleNames.ROLE_USER));
            if(Arrays.asList(AuthConstant.AdminAuthInfo.ADMIN_ACCOUNTS).contains(getUsername()));
            add(new SimpleGrantedAuthority(AuthConstant.RoleNames.ROLE_ADMIN));
        }};
    }

    @Override
    public String getPassword() {
        return sysAgent.getPassword();
    }

    @Override
    public String getUsername() {
        return sysAgent.getAccount().toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
