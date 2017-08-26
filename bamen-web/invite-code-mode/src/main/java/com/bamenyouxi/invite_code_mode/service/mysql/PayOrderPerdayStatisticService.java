package com.bamenyouxi.invite_code_mode.service.mysql;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.PayOrderPerdayStatisticMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.PayOrderPerdayStatistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 订单统计 service
 * Created by 13477 on 2017/7/10.
 */
@Service
public class PayOrderPerdayStatisticService extends AbstractCrudService<PayOrderPerdayStatistic, Long> {
	@Autowired
	private PayOrderPerdayStatisticMapper payOrderPerdayStatisticMapper;

	@Override
	public CrudMapper<PayOrderPerdayStatistic, Long> getMapper() {
		return payOrderPerdayStatisticMapper;
	}

	@Override
	protected void listBefore(Map<String, Object> params) {
		params.put(FieldConstant.CommonFieldConstant.groupBy.name(), FieldConstant.GroupConstant.CREATE_DATE);
	}
}
