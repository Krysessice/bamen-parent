package com.bamenyouxi.invite_code_mode.web.web_api.mysql;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.core.impl.web.AbstractCrudController;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.PayOrder;
import com.bamenyouxi.invite_code_mode.service.mysql.JuniorPayOrderService;
import com.bamenyouxi.invite_code_mode.util.UserDetailsUtil;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 下级代理订单 controller
 * Created by 13477 on 2017/7/10.
 */
@RestController
@RequestMapping("/order/junior")
final class JuniorPayOrderController extends AbstractCrudController<PayOrder, Long> {
	@Autowired
	private JuniorPayOrderService juniorPayOrderService;

	@Override
	public AbstractCrudService<PayOrder, Long> getService() {
		return juniorPayOrderService;
	}


}
