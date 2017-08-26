package com.bamenyouxi.room_card_mode.config.SecurityConfig;

import com.bamenyouxi.room_card_mode.model.Spring.CustomUser;
import com.bamenyouxi.room_card_mode.util.UserDetailsUtil;
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

@Component
class CustomLoginHandle implements AuthenticationSuccessHandler, LogoutSuccessHandler {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		if (UserDetailsUtil.isAdmin()) {
			response.sendRedirect("/admin/index.html");
			return;
		}
		response.sendRedirect("/agent/index.html");
	}

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		String username = ((CustomUser) authentication.getPrincipal()).getUsername();
		stringRedisTemplate.delete(UserDetailsUtil.getRedisKey_Salt(username));
		response.sendRedirect("/login?logout");
	}
}
