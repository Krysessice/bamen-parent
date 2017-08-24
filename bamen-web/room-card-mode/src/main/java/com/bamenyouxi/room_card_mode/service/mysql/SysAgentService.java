package com.bamenyouxi.room_card_mode.service.mysql;

import com.bamenyouxi.core.constant.AuthConstant;
import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.room_card_mode.constant.SysConstant;
import com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode.SysAgentExtendMapper;
import com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode.SysAgentMapper;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.SysAgent;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.SysAgentExtend;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashMap;

/**
 * 后台代理 service
 * Created by 13477 on 2017/7/10.
 */
@Service
public class SysAgentService extends AbstractCrudService<SysAgent, Long> implements CommandLineRunner {

	@Autowired
	private SysAgentMapper sysAgentMapper;
	@Autowired
	private SysAgentExtendMapper sysAgentExtendMapper;

	@Override
	public CrudMapper<SysAgent, Long> getMapper() {
		return sysAgentMapper;
	}

	/**
	 * 检验账号是否存在
	 * @param account   账号
	 * @return  是否存在的标识
	 */
	private boolean exist(String account) {
		long count = PageHelper.count(
				() -> getMapper().get(new HashMap<String, Object>() {{
					put(FieldConstant.DBFieldConstant.F_ACCOUNT.name(), account);
				}})
		);
		return count > 0;
	}

	@Override
	protected void saveBefore(SysAgent sysAgent) {
		Assert.notNull(sysAgent, TipMsgConstant.PARAM_INVALID);
		Assert.isTrue(sysAgent.getAccount() != null && sysAgent.getAccount().length() == SysConstant.getAccountLength(), TipMsgConstant.PARAM_INVALID);
		Assert.notNull(sysAgent.getNickName(), TipMsgConstant.PARAM_INVALID);
		super.saveBefore(sysAgent);
	}

	@Transactional
	@Override
	public SysAgent save(SysAgent sysAgent) {
		this.saveBefore(sysAgent);
		SysAgent.Builder.defaultPwdInject(sysAgent);
		int i = getMapper().save(sysAgent);
		if (i > 0) {
			int j = sysAgentExtendMapper.save(new SysAgentExtend.Builder().sysAgentId(sysAgent.getId()).build());
			Assert.isTrue(j > 0, TipMsgConstant.OPERATION_FAILED);
		}
		return sysAgent;
	}

	@Override
	public void run(String... strings) throws Exception {

		// 管理员账号初始化
		SysAgent sysAgent;
		for (String account : AuthConstant.AdminAuthInfo.ADMIN_ACCOUNTS) {
			if (account.length() == SysConstant.getAccountLength()) {
				if (this.exist(account)) continue;

				sysAgent =
						new SysAgent.Builder()
								.account(account)
								.nickName(AuthConstant.AdminAuthInfo.ADMIN_NICK_NAME)
								.build();
				SysAgent.Builder.defaultPwdInject(sysAgent);
				this.save(sysAgent);
			}
		}
	}
}
