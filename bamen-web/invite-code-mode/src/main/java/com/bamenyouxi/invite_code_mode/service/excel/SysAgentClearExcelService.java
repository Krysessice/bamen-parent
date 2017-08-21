package com.bamenyouxi.invite_code_mode.service.excel;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.FileConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.model.excel.ClearExcel;
import com.bamenyouxi.core.util.PoiExcelUtil;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.SysAgentMapper;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.TeamPayOrderMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SysAgent;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SystemInfo;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.TeamPayOrder;
import com.bamenyouxi.invite_code_mode.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

/**
 * 代理结算报表service
 * Created by 13477 on 2017/7/31.
 */
@Service
public class SysAgentClearExcelService {
	@Autowired
	private TeamPayOrderMapper teamPayOrderMapper;
	@Autowired
	private SysAgentMapper sysAgentMapper;
	@Autowired
	private RedisUtil redisUtil;

	public void makeExcel(String startDate, String endDate) throws IOException {
		Assert.isTrue(LocalDate.parse(startDate).compareTo(LocalDate.parse(endDate)) <= 0, TipMsgConstant.PARAM_INVALID);
		ClearExcel.Builder builder = this.buildTitle(startDate, endDate);
		List<SysAgent> list = this.getClearList(startDate, endDate);
		this.buildBody(builder, list);
		PoiExcelUtil.getInstance(builder.build()).createExcel();
	}

	/**
	 * 报表头部
	 * @param startDate 起始日期
	 * @param endDate   结束日期
	 * @return  ClearExcel.Builder
	 */
	private ClearExcel.Builder buildTitle(String startDate, String endDate) {
		return
			new ClearExcel.Builder()
				.parent(FileConstant.ExcelConstant.CLEAR_EXCEL_PARENT)
				.child(new StringBuffer(startDate).append("至").append(endDate).append("代理结算报表.xlsx").toString())
				.sheetName("代理结算报表")
				.titles(new ArrayList<String>() {{
					add("账户类型");add("银行账号");add("帐户名称");add("金额");add("打款原因");
					add("开户银行");add("支行信息");add("省");add("市");add("转出账号/卡");
					add("付款时间");add("转账状态");add("游戏id");add("昵称");
				}});
	}

	/**
	 * 报表数据体
	 * @param builder   ClearExcel.Builder
	 * @param list      List<SysAgent>
	 */
	private void buildBody(ClearExcel.Builder builder, List<SysAgent> list) {
		list.forEach(item -> builder.fields(
			new ClearExcel.Builder.BuilderField()
				.bankAccount(item.getBankAccount())
				.accountName(item.getRealName())
				.clearPrice(item.getClearPrice())
				.openingBank(item.getOpeningBank())
				.province(item.getProvince())
				.city(item.getCity())
				.gameId(item.getGameId())
				.nickName(item.getNickName())
				.build()
		));
		ClearExcel.Builder.BuilderField.reset();
	}

	/**
	 * 获取报表数据体
	 * @param startDate 起始日期
	 * @param endDate   结束日期
	 * @return  List<SysAgent>
	 */
	private List<SysAgent> getClearList(String startDate, String endDate) {
		List<SysAgent> list = this.sysAgentMapper.get(new HashMap<String, Object>() {{
			put(FieldConstant.DBFieldConstant.F_IS_AUTHORIZED.name(), true);
		}});

		SystemInfo systemInfo = redisUtil.getSystemInfo();
		Collection<SysAgent> removeList = new HashSet<>();
		list.forEach(sysAgent -> {
			BigDecimal clearPrice = new BigDecimal(0);
			for (TeamPayOrder teamPayOrder : this.teamPayOrderMapper.sumDaily(new HashMap<String, Object>() {{
				put(FieldConstant.ModelFieldConstant.superAgentGameId.name(), sysAgent.getGameId());
				put(FieldConstant.CommonFieldConstant.t1.name(), systemInfo.getT1_commission());
				put(FieldConstant.CommonFieldConstant.t2.name(), systemInfo.getT2_commission());
				put(FieldConstant.CommonFieldConstant.t3.name(), systemInfo.getT3_commission());
				put(FieldConstant.CommonFieldConstant.startDate.name(), startDate);
				put(FieldConstant.CommonFieldConstant.endDate.name(), endDate);
			}}))
				clearPrice = clearPrice.add(teamPayOrder.getCommission());
			if (clearPrice.doubleValue() == 0)
				removeList.add(sysAgent);
			sysAgent.setClearPrice(clearPrice);
		});

		list.removeAll(removeList);

		return list;
	}
}
