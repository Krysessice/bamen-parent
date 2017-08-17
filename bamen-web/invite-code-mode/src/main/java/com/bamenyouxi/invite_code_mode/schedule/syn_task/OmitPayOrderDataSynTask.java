package com.bamenyouxi.invite_code_mode.schedule.syn_task;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.impl.schedule.BaseScheduleTask;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.PayOrderMapper;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.PayOrderPerdayStatisticMapper;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.treasure.OnLineOrderMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.PayOrder;
import com.bamenyouxi.invite_code_mode.model.sqlserver.treasure.OnLineOrder;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

/**
 * 遗漏订单数据扫描
 * Created by 13477 on 2017/8/1.
 */
@Component
@Order(SysConstant.CommandLineOrder.OMIT_PAY_ORDER_DATA_SYN_TASK)
class OmitPayOrderDataSynTask implements BaseScheduleTask {
	private static String startDate;
	private static String endDate;

	@Autowired
	private PayOrderMapper payOrderMapper;
	@Autowired
	private OnLineOrderMapper onLineOrderMapper;
	@Autowired
	private PayOrderPerdayStatisticMapper payOrderPerdayStatisticMapper;

	@Override
	@Scheduled(cron = "59 59 10,4,23 * * ?")
	public void doTask() {
		startDate = endDate = LocalDate.now().toString();
		if (this.countOnlineOrder() > this.countPayOrder())
			this.omitPayOrderSyn();
	}

	@Override
	public void run(String... strings) throws Exception {
		startDate = LocalDate.now().minusDays(7).toString();
		endDate = LocalDate.now().toString();
		if (this.countOnlineOrder() > this.countPayOrder())
			this.omitPayOrderSyn();
	}

	/**
	 * 指定日期扫描订单总数
	 * @return  long
	 */
	private long countPayOrder() {
		return
				PageHelper.count(() ->
					payOrderMapper.get(new HashMap<String, Object>() {{
						put(FieldConstant.CommonFieldConstant.startDate.name(), startDate);
						put(FieldConstant.CommonFieldConstant.endDate.name(), endDate);
					}})
				);
	}

	/**
	 * 指定日期游戏订单总数
	 * @return  long
	 */
	private long countOnlineOrder() {
		return
				PageHelper.count(() ->
					onLineOrderMapper.get(new HashMap<String, Object>() {{
						put(FieldConstant.CommonFieldConstant.startDate.name(), startDate);
						put(FieldConstant.CommonFieldConstant.endDate.name(), endDate);
					}})
				);
	}

	/**
	 * 遗漏订单数据回扫
	 */
	private void omitPayOrderSyn() {
		List<OnLineOrder> onLineOrders = onLineOrderMapper.get(new HashMap<String, Object>() {{
			put(FieldConstant.CommonFieldConstant.startDate.name(), startDate);
			put(FieldConstant.CommonFieldConstant.endDate.name(), endDate);
		}});

		onLineOrders.forEach(
			onLineOrder -> payOrderMapper.save(
				new PayOrder.Builder()
					.orderNo(onLineOrder.getOrderId())
					.userId(onLineOrder.getUserId())
					.gameId(onLineOrder.getGameId())
					.accounts(onLineOrder.getAccounts())
					.payPrice(onLineOrder.getOrderAmount())
					.cardGold(onLineOrder.getCardGold())
					.createTime(onLineOrder.getApplyDate())
					.build()
			)
		);

		LocalDate _startDate = LocalDate.parse(startDate);
		LocalDate _endDate = LocalDate.parse(endDate);
		while (_startDate.compareTo(_endDate) <= 0) {
			payOrderPerdayStatisticMapper.payOrderPerdayStatistic(_startDate.toString()).forEach(
					item -> payOrderPerdayStatisticMapper.save(item)
			);
			_startDate = _startDate.plusDays(1);
		}
	}
}
