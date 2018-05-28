package com.bamenyouxi.invite_code_mode.config.SecurityConfig;

import com.bamenyouxi.core.constant.*;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.SysAgentMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SysAgent;
import com.bamenyouxi.invite_code_mode.model.spring.CustomUser;
import com.bamenyouxi.invite_code_mode.util.RedisUtil;
import com.bamenyouxi.invite_code_mode.util.UserDetailsUtil;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

/**
 * 自定义登录拦截器
 * Created by 13477 on 2017/6/28.
 */
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
	private void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
					.antMatchers("/favicon.ico", "/bootstrap/**", "/custom/**").permitAll() //static files
					.antMatchers("/admin/**", "/agent/get/**").hasRole(AuthConstant.RoleNames.ADMIN)
					.antMatchers("/agent/**").hasRole(AuthConstant.RoleNames.USER)
//					.antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
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
					.tokenValiditySeconds(30 * 24 * 60 * 60);

	}

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
			List<SysAgent> list = sysAgentMapper.get(new HashMap<String, Object>(){{
				put(FieldConstant.DBFieldConstant.F_GAME_ID.name(), s);
				put(FieldConstant.DBFieldConstant.F_SYS_FLAG.name(), SysConstant.SysFlagConstant.ENABLE);
			}});

			Assert.notEmpty(list, TipMsgConstant.SYS_AGENT_INFO_INVALID);
			Assert.isTrue(list.get(0).getAuthorized(), TipMsgConstant.ACCESS_TO_RESOURCES_WITHOUT_AUTHORITY);

			return new CustomUser(list.get(0));
		};
	}

	@Bean
	SaltSource customSaltSource() {

		return u -> {
			Assert.isTrue(u instanceof CustomUser, TipMsgConstant.SYS_AGENT_INFO_INVALID);
			SysAgent sysAgent = ((CustomUser) u).getSysAgent();
			String salt = sysAgent.getSecretKey();
			redisUtil.saveSalt(u.getUsername(), salt);
			sysAgent.emptySecretKey();
			return salt;
		};
	}

}
