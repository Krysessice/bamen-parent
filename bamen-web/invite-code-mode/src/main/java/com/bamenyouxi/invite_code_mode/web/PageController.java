package com.bamenyouxi.invite_code_mode.web;

import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SystemInfo;
import com.bamenyouxi.invite_code_mode.util.RedisUtil;
import com.bamenyouxi.invite_code_mode.util.UserDetailsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 页面视图控制
 * Created by 13477 on 2017/6/23.
 */
@Controller
final class PageController {
	@Autowired
	private RedisUtil redisUtil;

	@GetMapping({"", "/"})
	private String index() {
		return UserDetailsUtil.isAdmin() ? "admin/index" : "agent/index";
	}

	@GetMapping("/login")
	private String login() {
		return "login";
	}

	@GetMapping({"/{viewName}.html", "/{viewName}.htm"})
	private String page(@PathVariable String viewName) {
		return viewName;
	}

	@GetMapping({"/demo/{viewName}.html", "/demo/{viewName}.htm"})
	private String pageDemo(@PathVariable String viewName) {
		return "demo/" + viewName;
	}

	@GetMapping({"/admin/{viewName}.html", "/admin/{viewName}.htm"})
	private String pageAdmin(@PathVariable String viewName) {
		return "admin/" + viewName;
	}

	@GetMapping({"/agent/{viewName}.html", "/agent/{viewName}.htm"})
	private String pageAgent(@PathVariable String viewName) {
		return "agent/" + viewName;
	}

	@ModelAttribute("systemInfo")
	public SystemInfo systemInfo() {
		return redisUtil.getSystemInfo();
	}
}
