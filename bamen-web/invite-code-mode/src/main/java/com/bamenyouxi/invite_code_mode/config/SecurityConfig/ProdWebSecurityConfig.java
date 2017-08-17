package com.bamenyouxi.invite_code_mode.config.SecurityConfig;

import com.bamenyouxi.core.constant.YmlConstant;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * 视图拦截器(生产环境)
 * Created by 13477 on 2017/6/19.
 */
@Profile(YmlConstant.ProfileName.PROD)
@Configuration
class ProdWebSecurityConfig extends AbstractCustomWebSecurityConfig {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf()
					.disable()
				.authorizeRequests()
					.antMatchers("/test/**").denyAll();
		super.configure(http);
	}
}
