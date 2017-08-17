package com.bamenyouxi.invite_code_mode.config.SecurityConfig;

import com.bamenyouxi.invite_code_mode.model.spring.CustomUser;
import com.bamenyouxi.invite_code_mode.util.UserDetailsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功、登出跳转控制器
 * Created by 13477 on 2017/6/30.
 */
@Component
final class CustomLoginHandle implements AuthenticationSuccessHandler, LogoutSuccessHandler {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		if (UserDetailsUtil.isAdmin()) {
			response.sendRedirect("/admin/index.html");
			return;
		}
		response.sendRedirect(UserDetailsUtil.isFinishInfo() ? "/agent/index.html" : "/agent/cover.html");
	}

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		String username = ((CustomUser) authentication.getPrincipal()).getUsername();
		stringRedisTemplate.delete(UserDetailsUtil.getRedisKey_Salt(username));
		response.sendRedirect("/login?logout");
	}
}
