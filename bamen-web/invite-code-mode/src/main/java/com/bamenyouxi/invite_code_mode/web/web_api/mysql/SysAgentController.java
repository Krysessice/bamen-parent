package com.bamenyouxi.invite_code_mode.web.web_api.mysql;

import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.impl.web.AbstractCrudController;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SysAgent;
import com.bamenyouxi.invite_code_mode.service.mysql.SysAgentService;
import com.bamenyouxi.invite_code_mode.util.UserDetailsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后台代理 controller
 * Created by 13477 on 2017/7/10.
 */
@RestController
@RequestMapping("/agent")
final class SysAgentController extends AbstractCrudController<SysAgent, Long> {
	@Autowired
	private SysAgentService sysAgentService;

	@Override
	public AbstractCrudService<SysAgent, Long> getService() {
		return sysAgentService;
	}

	@PutMapping("/authorize/{id}/")
	private WebResult authorize(@PathVariable Long id) {
		sysAgentService.authorize(id);
		return WebResult.of();
	}

	@PutMapping("/info/")
	private WebResult updateInfo(String realName, String tel, String openingBank, String bankAccount) {
		Assert.isTrue(!UserDetailsUtil.isFinishInfo(), TipMsgConstant.OPERATION_EXCEPTION_AND_LOGOUT);
		SysAgent sysAgent = sysAgentService.update(new SysAgent.Builder()
				.id(UserDetailsUtil.getId())
				.realName(realName).tel(tel)
				.openingBank(openingBank)
				.bankAccount(bankAccount)
				.isFinishInfo(true).build());
		UserDetailsUtil.refresh(sysAgent);
		return WebResult.of();
	}

	@PutMapping("/pwd/")
	private WebResult updatePwd(String oldPwd, String newPwd) {
		sysAgentService.changePassword(oldPwd, newPwd);
		return WebResult.of();
	}

	@PutMapping("/announce/close/")
	private WebResult closeAnnounce() {
		Assert.isTrue(UserDetailsUtil.isShowAnnounce(), TipMsgConstant.OPERATION_EXCEPTION_AND_LOGOUT);
		SysAgent sysAgent = sysAgentService.update(new SysAgent.Builder()
				.id(UserDetailsUtil.getId())
				.showAnnounce(false).build());
		UserDetailsUtil.refresh(sysAgent);
		return WebResult.of();
	}
}
