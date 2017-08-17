package com.bamenyouxi.invite_code_mode.service.mysql;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.TeamPayOrderMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SystemInfo;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.TeamPayOrder;
import com.bamenyouxi.invite_code_mode.util.RedisUtil;
import com.bamenyouxi.invite_code_mode.util.UserDetailsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 团队充值统计 service
 * Created by 13477 on 2017/7/11.
 */
@Service
public class TeamPayOrderService extends AbstractCrudService<TeamPayOrder, Long> {

	@Autowired
	private TeamPayOrderMapper teamPayOrderMapper;
	@Autowired
	private RedisUtil redisUtil;

	@Override
	public CrudMapper<TeamPayOrder, Long> getMapper() {
		return teamPayOrderMapper;
	}

	@Override
	protected void listBefore(Map<String, Object> params) {
		SystemInfo systemInfo = redisUtil.getSystemInfo();
		params.put(FieldConstant.ModelFieldConstant.superAgentGameId.name(), UserDetailsUtil.getGameId());
		params.put(FieldConstant.CommonFieldConstant.t1.name(), systemInfo.getT1_commission());
		params.put(FieldConstant.CommonFieldConstant.t2.name(), systemInfo.getT2_commission());
		params.put(FieldConstant.CommonFieldConstant.t3.name(), systemInfo.getT3_commission());
	}

	public PageInfo<TeamPayOrder> sumActual(int page, int size, Map<String, Object> params) {
		this.listBefore(params);
		PageHelper.startPage(page, size, false);
		List<TeamPayOrder> list = teamPayOrderMapper.sumActual(params);
		return new PageInfo<>(list);
	}

	public PageInfo<TeamPayOrder> sumDaily(int page, int size, Map<String, Object> params) {
		this.listBefore(params);
		PageHelper.startPage(page, size, false);
		List<TeamPayOrder> list = teamPayOrderMapper.sumDaily(params);
		return new PageInfo<>(list);
	}
}
