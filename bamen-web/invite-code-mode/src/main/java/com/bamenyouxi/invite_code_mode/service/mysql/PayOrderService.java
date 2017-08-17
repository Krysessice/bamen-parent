package com.bamenyouxi.invite_code_mode.service.mysql;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.PayOrderMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.PayOrder;
import com.bamenyouxi.invite_code_mode.util.SysResourceUtil;
import com.bamenyouxi.invite_code_mode.util.UserDetailsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 后台代理 订单 service
 * Created by 13477 on 2017/7/10.
 */
@Service
public class PayOrderService extends AbstractCrudService<PayOrder, Long> {
	@Autowired
	private PayOrderMapper payOrderMapper;
	@Autowired
	private SysResourceUtil sysResourceUtil;

	@Override
	public CrudMapper<PayOrder, Long> getMapper() {
		return payOrderMapper;
	}

	@Override
	protected void listBefore(Map<String, Object> params) {
		sysResourceUtil.exists(UserDetailsUtil.getId(), UserDetailsUtil.getId(), SysConstant.SysResourceConstant.RESOURCE_PAY_ORDER);
		params.put(FieldConstant.DBFieldConstant.F_GAME_ID.name(), UserDetailsUtil.getGameId());
		super.listBefore(params);
	}
}
