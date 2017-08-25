package com.bamenyouxi.room_card_mode.config.SecurityConfig;

import com.bamenyouxi.core.constant.*;
import com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode.SysAgentMapper;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.SysAgent;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.impl.Spring.CustomUser;
import com.bamenyouxi.room_card_mode.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;

@Configuration
@EnableWebSecurity
abstract class AbstractCustomWebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private SysAgentMapper sysAgentMapper;

	@Autowired
	private CustomLoginHandle customLoginHandle;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers("/favicon.ico", "/bootstrap/**", "/custom/**").permitAll()
				.antMatchers("/admin/**", "/agent/get/**").hasRole(AuthConstant.RoleNames.ADMIN)
				.antMatchers("/agent/**").hasRole(AuthConstant.RoleNames.USER)
				.antMatchers(WebConstant.PageUrl.LOGIN, "/login", "/login.htm").permitAll()
				.anyRequest().authenticated()
			.and()
				.formLogin()
				.loginPage("/login")
				.successHandler(customLoginHandle)
				.permitAll()
			.and()
				.logout()
				.deleteCookies("JSESSIONID")
				.logoutSuccessHandler(customLoginHandle)
				.permitAll()
			.and()
				.rememberMe()
				.userDetailsService(customUserDetailsService())
				.authenticationSuccessHandler(customLoginHandle)
				.tokenValiditySeconds(30 * 24 * 20 * 60);

	}


	/**
	 * 把密码加密
	 */
	@Bean
	AuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(customUserDetailsService());
		daoAuthenticationProvider.setSaltSource(customSaltSource());
		daoAuthenticationProvider.setPasswordEncoder(new Md5PasswordEncoder());
		return daoAuthenticationProvider;
	}

	@Bean
	UserDetailsService customUserDetailsService() {
		return s -> {
			List<SysAgent> list = sysAgentMapper.get(new HashMap<String, Object>() {{
				put(FieldConstant.DBFieldConstant.F_ACCOUNT.name(), s);
				put(FieldConstant.DBFieldConstant.F_SYS_FLAG.name(), SysConstant.SysFlagConstant.ENABLE);
			}});

			Assert.notEmpty(list, TipMsgConstant.SYS_AGENT_INFO_INVALID);

			return new CustomUser(list.get(0));
		};
	}


	@Bean
	SaltSource customSaltSource() {
		return u -> {
			Assert.isTrue(u instanceof CustomUser, TipMsgConstant.SYS_AGENT_INFO_INVALID);
			SysAgent sysAgent = ((CustomUser) u).getSysAgent();
			System.out.println(sysAgent + "---");
			String salt = sysAgent.getSecretKey();
			redisUtil.saveSalt(u.getUsername(), salt);
			sysAgent.emptySecretKey();
			return salt;
		};
	}
}
