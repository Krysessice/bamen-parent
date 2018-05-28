package com.bamenyouxi.cow_nn_mode.service.mysql;


import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.cow_nn_mode.mapper.mysql.cow_nn_mode.SysAgentClearRecordMapper;
import com.bamenyouxi.cow_nn_mode.mapper.mysql.cow_nn_mode.WithdrawMapper;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.SysAgentClearRecord;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.SystemInfo;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.Withdraw;
import com.bamenyouxi.cow_nn_mode.util.RedisUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

/**
 * 提现结算记录service
 * Created by 13477 on 2018/4/27.
 */
@Service
@Order(SysConstant.CommandLineOrder.SYS_WITHDIAW_RECORD_SERVICE_RUNNER)
public class SysAgentClearRecordService extends AbstractCrudService<SysAgentClearRecord, Long> implements CommandLineRunner {
	@Autowired
	private SysAgentClearRecordMapper sysAgentClearRecordMapper;
	@Autowired
	private WithdrawMapper withdrawMapper;
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
		System.out.println(latestClearDate);
		Assert.notNull(latestClearDate, TipMsgConstant.CONDITION_UNMET);
		System.out.println(sysAgentClearRecord.getStartDate());
//		Assert.isTrue(this.getExecuteDate(latestClearDate).equals(sysGoldClearRecord.getStartDate()), TipMsgConstant.PARAM_INVALID);
		redisUtil.saveSystemInfo(systemInfo.setLatestClearDate(sysAgentClearRecord.getEndDate()));
		super.saveBefore(sysAgentClearRecord);
	}

	@Transactional
	public void getCleanTime(String startDate, String endDate) {
		List<SysAgentClearRecord> list=sysAgentClearRecordMapper.gets();
		if(!list.isEmpty()){
			Date s= Date.valueOf(startDate);
			boolean JsDate=s.before(list.get(list.size()-1).getEndDate());
			Assert.isTrue(!JsDate,TipMsgConstant.SYS_CLEAN_TIME_GOLD);
		}
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
						.payPrice(this.getTotalPayPrice(startDate, endDate)).build();
	}

	/**
	 * 获取指定日期充值总额
	 * @param startDate 起始日期
	 * @param endDate   结束日期
	 * @return  BigDecimal totalPayPrice
	 */
	private BigDecimal getTotalPayPrice(String startDate, String endDate) {
		BigDecimal totalPayPrice = BigDecimal.valueOf(0);
		for (Withdraw item : withdrawMapper.gets(new HashMap<String, Object>() {{
			put(FieldConstant.CommonFieldConstant.groupBy.name(), FieldConstant.GroupConstant.CREATE_DATE);
			put(FieldConstant.CommonFieldConstant.startDate.name(), startDate);
			put(FieldConstant.CommonFieldConstant.endDate.name(), endDate);
		}}))
			totalPayPrice = totalPayPrice.add(item.getWithdraw());
		return totalPayPrice;
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
			List<Withdraw> list2 = withdrawMapper.get(new HashMap<>());
			return list2.isEmpty() ? null : Date.valueOf(list2.get(0).getCreateTime().toLocalDateTime().toLocalDate());
		}
		return list.get(0).getEndDate();
	}

	@Override
	public void run(String... strings) throws Exception {
		redisUtil.saveSystemInfo(redisUtil.getSystemInfo().setLatestClearDate(this.getLatestClearDate()));
	}
}
