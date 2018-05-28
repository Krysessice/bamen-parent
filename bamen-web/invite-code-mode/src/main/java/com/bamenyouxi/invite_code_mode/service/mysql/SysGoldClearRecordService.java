package com.bamenyouxi.invite_code_mode.service.mysql;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.*;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.*;
import com.bamenyouxi.invite_code_mode.util.RedisUtil;
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
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 金币结算记录service
 * Created by 13477 on 2018/4/27.
 */
@Service
@Order(SysConstant.CommandLineOrder.SYS_Gold_CLEAR_RECORD_SERVICE_RUNNER)
public class SysGoldClearRecordService extends AbstractCrudService<SysGoldClearRecord, Long> implements CommandLineRunner {
	@Autowired
	private SysGoldClearRecordMapper sysGoldClearRecordMapper;
	@Autowired
	private UserGoldMapper userGoldMapper;
	@Autowired
	private RedisUtil redisUtil;

	@Override
	public CrudMapper<SysGoldClearRecord, Long> getMapper() {
		return sysGoldClearRecordMapper;
	}

	@Override
	protected void saveBefore(SysGoldClearRecord sysGoldClearRecord) {
		GoldInfo goldInfo = redisUtil.getGoldInfo();
		Date latestClearDate = goldInfo.getLatestClearDate();
		System.out.println(latestClearDate);
		Assert.notNull(latestClearDate, TipMsgConstant.CONDITION_UNMET);
		System.out.println(sysGoldClearRecord.getStartDate());
//		Assert.isTrue(this.getExecuteDate(latestClearDate).equals(sysGoldClearRecord.getStartDate()), TipMsgConstant.PARAM_INVALID);
		redisUtil.saveGoldInfo(goldInfo.setLatestClearDate(sysGoldClearRecord.getEndDate()));
		super.saveBefore(sysGoldClearRecord);
	}

	@Transactional
	public void getCleanTime(String startDate, String endDate) throws ParseException {
		List<SysGoldClearRecord> list=sysGoldClearRecordMapper.gets();
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
	public SysGoldClearRecord getClear(String startDate, String endDate) {
		return
			new SysGoldClearRecord.Builder()
				.startDate(Date.valueOf(startDate))
				.endDate(Date.valueOf(endDate))
				.systemCost(this.getTotalPayPrice(startDate, endDate))
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
		for (UserGold item : userGoldMapper.gets(new HashMap<String, Object>() {{
			put(FieldConstant.CommonFieldConstant.groupBy.name(), FieldConstant.GroupConstant.CREATE_DATE);
			put(FieldConstant.CommonFieldConstant.startDate.name(), startDate);
			put(FieldConstant.CommonFieldConstant.endDate.name(), endDate);
		}}))
			totalPayPrice = totalPayPrice.add(item.getSystemcost());
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
		for (UserGold item : userGoldMapper.sumActual(new HashMap<String, Object>() {{
			put(FieldConstant.CommonFieldConstant.t1.name(), redisUtil.getGoldInfo().getT1_commission());
			put(FieldConstant.CommonFieldConstant.t2.name(), redisUtil.getGoldInfo().getT2_commission());
			put(FieldConstant.CommonFieldConstant.t3.name(), redisUtil.getGoldInfo().getT3_commission());
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
		List<SysGoldClearRecord> list = sysGoldClearRecordMapper.get(new HashMap<>());
		if (list.isEmpty()) {
			PageHelper.startPage(1, 1, FieldConstant.SortConstant.CREATE_TIME_ASC);
			List<UserGold> list2 = userGoldMapper.get(new HashMap<>());
			return list2.isEmpty() ? null : Date.valueOf(list2.get(0).getCreateTime().toLocalDateTime().toLocalDate());
		}
		return list.get(0).getEndDate();
	}

	@Override
	public void run(String... strings) throws Exception {
		redisUtil.saveGoldInfo(redisUtil.getGoldInfo().setLatestClearDate(this.getLatestClearDate()));
	}


}
