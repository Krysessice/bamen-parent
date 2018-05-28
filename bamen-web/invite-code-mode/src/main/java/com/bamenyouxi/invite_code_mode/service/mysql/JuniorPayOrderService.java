package com.bamenyouxi.invite_code_mode.service.mysql;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.FileConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.PayOrderMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.PayOrder;
import com.bamenyouxi.invite_code_mode.util.SysResourceUtil;
import com.bamenyouxi.invite_code_mode.util.UserDetailsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 下级代理 订单 service
 * Created by 13477 on 2017/7/10.
 */
@Service
public class JuniorPayOrderService extends AbstractCrudService<PayOrder, Long> {
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
		Object gameId = params.get(FieldConstant.DBFieldConstant.F_GAME_ID.name());
		if (gameId != null) {
			super.checkParamsType(new HashMap<Object, Class<?>>() {{
				put(gameId, Integer.class);
			}});
			sysResourceUtil.exists(UserDetailsUtil.getGameId(), (Integer) gameId, SysConstant.SysResourceConstant.RESOURCE_PAY_ORDER);
		} else {
			params.put(FieldConstant.ModelFieldConstant.superAgentGameId.name(), UserDetailsUtil.getGameId());
		}
		super.listBefore(params);
	}


}
