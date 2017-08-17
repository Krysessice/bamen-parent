package com.bamenyouxi.invite_code_mode.web.web_api.mysql;

import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.impl.web.AbstractCrudController;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.TeamPayOrder;
import com.bamenyouxi.invite_code_mode.service.mysql.TeamPayOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 团队充值统计 controller
 * Created by 13477 on 2017/7/10.
 */
@RestController
@RequestMapping("/order/team")
final class TeamPayOrderController extends AbstractCrudController<TeamPayOrder, Long> {
	@Autowired
	private TeamPayOrderService teamPayOrderService;

	@Override
	public AbstractCrudService<TeamPayOrder, Long> getService() {
		return teamPayOrderService;
	}

	@GetMapping("/actual/list/")
	private WebResult sumActual(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
			@RequestParam Map<String, Object> params) {
		return WebResult.of(teamPayOrderService.sumActual(page, size, params));
	}

	@GetMapping("/daily/list/")
	private WebResult sumDaily(
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_PAGE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_PAGE) Integer page,
			@RequestParam(value = SysConstant.PageConstant.DEFAULT_SIZE_NAME, defaultValue = "" + SysConstant.PageConstant.DEFAULT_SIZE) Integer size,
			@RequestParam Map<String, Object> params) {
		return WebResult.of(teamPayOrderService.sumDaily(page, size, params));
	}
}
