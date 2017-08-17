package com.bamenyouxi.invite_code_mode.schedule.syn_task;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.impl.schedule.AbstractDataSynTask;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.PayOrderMapper;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.treasure.OnLineOrderMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.PayOrder;
import com.bamenyouxi.invite_code_mode.model.sqlserver.treasure.OnLineOrder;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;

/**
 * 订单数据同步
 * Created by 13477 on 2017/7/14.
 */
@Component
@Order(SysConstant.CommandLineOrder.PAY_ORDER_DATA_SYN_TASK)
class PayOrderDataSynTask extends AbstractDataSynTask<OnLineOrder, PayOrder> {
	private static Timestamp createTime;

	@Autowired
	private PayOrderMapper payOrderMapper;
	@Autowired
	private OnLineOrderMapper onLineOrderMapper;

	@Override
	protected List<PayOrder> dataScanForRun() {
		createTime = Timestamp.from(Instant.now());
		PageHelper.startPage(1, 1, FieldConstant.SortConstant.CREATE_TIME_DESC);
		List<PayOrder> list = this.payOrderMapper.get(new HashMap<>());
		Timestamp temp = list.isEmpty() ? null : list.get(0).getCreateTime();
		return
				onLineOrderMapper.getOfPayOrder(new HashMap<String, Object>() {{
					put(FieldConstant.CommonFieldConstant.startTime.name(), temp);
				}});
	}

	@Override
	protected List<PayOrder> dataScanForTask() {
		List<PayOrder> payOrders = onLineOrderMapper.getOfPayOrder(new HashMap<String, Object>() {{
			put(FieldConstant.CommonFieldConstant.startTime.name(), createTime);
		}});
		createTime = Timestamp.from(Instant.now());
		return payOrders;
	}

	@Override
	protected void dataSyn(List<PayOrder> list) {
		if (super.dataSynBefore(list))
			this.payOrderMapper.saveMul(list);
	}

	@Override
	@Scheduled(initialDelay = SysConstant.ScheduleTask.PAY_ORDER_SYN_DELAY, fixedDelay = SysConstant.ScheduleTask.PAY_ORDER_SYN_INTERVAL)
	public void doTask() {
		super.doTask();
	}
}
