package com.bamenyouxi.invite_code_mode.service.mysql;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.PayOrderPerdayStatisticMapper;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.SysAgentClearRecordMapper;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.TeamPayOrderMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.PayOrderPerdayStatistic;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SysAgentClearRecord;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SystemInfo;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.TeamPayOrder;
import com.bamenyouxi.invite_code_mode.util.RedisUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 结算记录service
 * Created by 13477 on 2017/7/25.
 */
@Service
@Order(SysConstant.CommandLineOrder.SYS_AGENT_CLEAR_RECORD_SERVICE_RUNNER)
public class SysAgentClearRecordService extends AbstractCrudService<SysAgentClearRecord, Long> implements CommandLineRunner {
	@Autowired
	private SysAgentClearRecordMapper sysAgentClearRecordMapper;
	@Autowired
	private PayOrderPerdayStatisticMapper payOrderPerdayStatisticMapper;
	@Autowired
	private TeamPayOrderMapper teamPayOrderMapper;
	@Autowired
	private RedisUtil redisUtil;

	@Override
	public CrudMapper<SysAgentClearRecord, Long> getMapper() {
		return sysAgentClearRecordMapper;
	}

	@Override
	protected void saveBefore(SysAgentClearRecord sysAgentClearRecord) {
		SystemInfo systemInfo = redisUtil.getSystemInfo();
		Date latestClearDate = systemInfo.getLatestClearDate();
		Assert.notNull(latestClearDate, TipMsgConstant.CONDITION_UNMET);
		Assert.isTrue(this.getExecuteDate(latestClearDate).equals(sysAgentClearRecord.getStartDate()), TipMsgConstant.PARAM_INVALID);
		redisUtil.saveSystemInfo(systemInfo.setLatestClearDate(sysAgentClearRecord.getEndDate()));
		super.saveBefore(sysAgentClearRecord);
	}

	/**
	 * 获取指定日期充值总额及结算总额
	 * @param startDate 起始日期
	 * @param endDate   结束日期
	 * @return  SysAgentClearRecord
	 */
	public SysAgentClearRecord getClear(String startDate, String endDate) {
		return
			new SysAgentClearRecord.Builder()
				.startDate(Date.valueOf(startDate))
				.endDate(Date.valueOf(endDate))
				.payPrice(this.getTotalPayPrice(startDate, endDate))
				.clearPrice(this.getTotalClearPrice(startDate, endDate)).build();
	}

	/**
	 * 获取指定日期充值总额
	 * @param startDate 起始日期
	 * @param endDate   结束日期
	 * @return  BigDecimal totalPayPrice
	 */
	private BigDecimal getTotalPayPrice(String startDate, String endDate) {
		BigDecimal totalPayPrice = BigDecimal.valueOf(0);
		for (PayOrderPerdayStatistic item : payOrderPerdayStatisticMapper.get(new HashMap<String, Object>() {{
			put(FieldConstant.CommonFieldConstant.groupBy.name(), FieldConstant.GroupConstant.CREATE_DATE);
			put(FieldConstant.CommonFieldConstant.startDate.name(), startDate);
			put(FieldConstant.CommonFieldConstant.endDate.name(), endDate);
		}}))
			totalPayPrice = totalPayPrice.add(item.getPayPrice());
		return totalPayPrice;
	}

	/**
	 * 获取指定日期结算总额
	 * @param startDate 起始日期
	 * @param endDate   结束日期
	 * @return  BigDecimal totalClearPrice
	 */
	private BigDecimal getTotalClearPrice(String startDate, String endDate) {
		BigDecimal totalClearPrice = BigDecimal.valueOf(0);
		for (TeamPayOrder item : teamPayOrderMapper.sumDaily(new HashMap<String, Object>() {{
			put(FieldConstant.CommonFieldConstant.t1.name(), redisUtil.getSystemInfo().getT1_commission());
			put(FieldConstant.CommonFieldConstant.t2.name(), redisUtil.getSystemInfo().getT2_commission());
			put(FieldConstant.CommonFieldConstant.t3.name(), redisUtil.getSystemInfo().getT3_commission());
			put(FieldConstant.CommonFieldConstant.startDate.name(), startDate);
			put(FieldConstant.CommonFieldConstant.endDate.name(), endDate);
		}}))
			totalClearPrice = totalClearPrice.add(item.getCommission());
		return totalClearPrice;
	}

	/**
	 * 获取结算起始（执行）日期
	 * @param latestClearDate   最近结算日期
	 * @return  java.sql.Date
	 */
	private Date getExecuteDate(Date latestClearDate) {
		return Date.valueOf(latestClearDate.toLocalDate().plusDays(1L));
	}

	/**
	 * 获取最近结算日期
	 * @return  java.sql.Date
	 */
	private Date getLatestClearDate() {
		PageHelper.startPage(1, 1, FieldConstant.SortConstant.END_DATE_DESC);
		List<SysAgentClearRecord> list = sysAgentClearRecordMapper.get(new HashMap<>());
		if (list.isEmpty()) {
			PageHelper.startPage(1, 1, FieldConstant.SortConstant.CREATE_TIME_ASC);
			List<PayOrderPerdayStatistic> list2 = payOrderPerdayStatisticMapper.get(new HashMap<>());
			return list2.isEmpty() ? null : Date.valueOf(list2.get(0).getCreateTime().toLocalDateTime().toLocalDate());
		}
		return list.get(0).getEndDate();
	}

	@Override
	public void run(String... strings) throws Exception {
		redisUtil.saveSystemInfo(redisUtil.getSystemInfo().setLatestClearDate(this.getLatestClearDate()));
	}
}
