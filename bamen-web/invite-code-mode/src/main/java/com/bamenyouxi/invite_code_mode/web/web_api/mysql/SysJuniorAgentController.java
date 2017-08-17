package com.bamenyouxi.invite_code_mode.web.web_api.mysql;

import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.impl.web.AbstractCrudController;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SysAgent;
import com.bamenyouxi.invite_code_mode.service.mysql.SysJuniorAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 下级代理 controller
 * Created by 13477 on 2017/7/10.
 */
@RestController
@RequestMapping("/agent/junior")
final class SysJuniorAgentController extends AbstractCrudController<SysAgent, Long> {
	@Autowired
	private SysJuniorAgentService sysJuniorAgentService;

	@Override
	public AbstractCrudService<SysAgent, Long> getService() {
		return sysJuniorAgentService;
	}
}
