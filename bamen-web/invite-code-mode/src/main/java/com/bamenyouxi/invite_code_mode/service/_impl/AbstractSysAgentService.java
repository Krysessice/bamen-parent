package com.bamenyouxi.invite_code_mode.service._impl;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SysAgent;
import com.bamenyouxi.invite_code_mode.util.SysResourceUtil;
import com.bamenyouxi.invite_code_mode.util.UserDetailsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * abstract class for {@link SysAgent}
 * Created by 13477 on 2017/7/15.
 */
public abstract class AbstractSysAgentService extends AbstractCrudService<SysAgent, Long> {
	@Autowired
	private SysResourceUtil sysResourceUtil;

	@Override
	protected void getBefore(Map<String, Object> params) {
		if (UserDetailsUtil.isAdmin()) {
			super.getBefore(params);
			return;
		}

		if (params.containsKey(FieldConstant.DBFieldConstant.F_ID.name())) {
			Object value = params.get(FieldConstant.DBFieldConstant.F_ID.name());
			super.checkParamsType(new HashMap<Object, Class<?>>() {{
				put(value, Long.class);
			}});
			sysResourceUtil.exists(UserDetailsUtil.getId(), (Long) value, SysConstant.SysResourceConstant.RESOURCE_USER);
		}
		if (params.containsKey(FieldConstant.DBFieldConstant.F_GAME_ID.name())) {
			Object value = params.get(FieldConstant.DBFieldConstant.F_GAME_ID.name());
			super.checkParamsType(new HashMap<Object, Class<?>>() {{
				put(value, Integer.class);
			}});
			sysResourceUtil.exists(UserDetailsUtil.getGameId(), (Integer) value, SysConstant.SysResourceConstant.RESOURCE_USER);
		}
	}

	protected SysAgent getSingleSysAgent(Map<String, Object> params) {
		List<SysAgent> list = getMapper().get(params);
		Assert.notEmpty(list, TipMsgConstant.SYS_AGENT_INFO_INVALID);
		return list.get(0);
	}
}
