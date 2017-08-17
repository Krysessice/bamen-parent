package com.bamenyouxi.invite_code_mode.web.web_api.mysql;

import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.impl.web.AbstractCrudController;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.PayOrder;
import com.bamenyouxi.invite_code_mode.service.mysql.PayOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单 controller
 * Created by 13477 on 2017/7/10.
 */
@RestController
@RequestMapping("/order")
final class PayOrderController extends AbstractCrudController<PayOrder, Long> {
	@Autowired
	private PayOrderService payOrderService;

	@Override
	public AbstractCrudService<PayOrder, Long> getService() {
		return payOrderService;
	}
}
