package com.bamenyouxi.room_card_mode.service.mysql;

import com.bamenyouxi.core.constant.AuthConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode.SysAgentMapper;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.SysAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

/**
 * 后台代理 service
 * Created by 13477 on 2017/7/10.
 */
@Service
public class SysAgentService extends AbstractCrudService implements CommandLineRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(SysAgentService.class);

	@Autowired
	private SysAgentMapper sysAgentMapper;

	@Override
	public CrudMapper<SysAgent, Long> getMapper() {
		return sysAgentMapper;
	}

	@Override
	public void run(String... strings) throws Exception {

		// 管理员账号初始化
		SysAgent sysAgent;
		for (String account : AuthConstant.AdminAuthInfo.ADMIN_ACCOUNTS) {
			if (account.length() == 11) {
				sysAgent =
						new SysAgent.Builder()
								.account(account)
								.nickName(AuthConstant.AdminAuthInfo.ADMIN_NICK_NAME)
								.build();
				SysAgent.Builder.defaultPwdInject(sysAgent);
				getMapper().save(sysAgent);
			}
		}
	}
}
