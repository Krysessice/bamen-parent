package com.bamenyouxi.invite_code_mode.web.web_api.mysql;

import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.impl.web.AbstractCrudController;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.PayOrderPerdayStatistic;
import com.bamenyouxi.invite_code_mode.service.mysql.JuniorPayOrderPerdayStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 下级代理 订单统计 controller
 * Created by 13477 on 2017/7/10.
 */
@RestController
@RequestMapping("/order/statistic/junior")
final class JuniorPayOrderStatisticController extends AbstractCrudController<PayOrderPerdayStatistic, Long> {
	@Autowired
	private JuniorPayOrderPerdayStatisticService juniorPayOrderPerdayStatisticService;

	@Override
	public AbstractCrudService<PayOrderPerdayStatistic, Long> getService() {
		return juniorPayOrderPerdayStatisticService;
	}
}
