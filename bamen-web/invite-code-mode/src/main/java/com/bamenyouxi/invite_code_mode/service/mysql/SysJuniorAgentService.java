package com.bamenyouxi.invite_code_mode.service.mysql;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.SysAgentMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SysAgent;
import com.bamenyouxi.invite_code_mode.service._impl.AbstractSysAgentService;
import com.bamenyouxi.invite_code_mode.util.UserDetailsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * 下级代理 service
 * Created by 13477 on 2017/7/10.
 */
@Service
public class SysJuniorAgentService extends AbstractSysAgentService {
	@Autowired
	private SysAgentMapper sysAgentMapper;

	@Override
	public CrudMapper<SysAgent, Long> getMapper() {
		return sysAgentMapper;
	}

	@Override
	protected void listBefore(Map<String, Object> params) {
		Assert.notNull(params.get(FieldConstant.DBFieldConstant.F_IS_AUTHORIZED.name()), TipMsgConstant.PARAM_INVALID);
		super.listBefore(params);
		params.put(FieldConstant.DBFieldConstant.F_SUPER_AGENT_GAME_ID.name(), UserDetailsUtil.getGameId());
	}
}
