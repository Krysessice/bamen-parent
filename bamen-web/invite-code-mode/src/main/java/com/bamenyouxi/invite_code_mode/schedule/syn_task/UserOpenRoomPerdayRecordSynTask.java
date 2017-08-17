package com.bamenyouxi.invite_code_mode.schedule.syn_task;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.impl.schedule.AbstractDataSynTask;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.UserOpenRoomPerdayRecordMapper;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.treasure.UserFangKaCostMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.UserOpenRoomPerdayRecord;
import com.bamenyouxi.invite_code_mode.model.sqlserver.treasure.UserFangKaCost;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

/**
 * 用户每日房卡消耗记录同步任务
 * Created by 13477 on 2017/8/4.
 */
@Component
@Order(SysConstant.CommandLineOrder.USER_OPEN_ROOM_PERDAY_RECORD_SYN_TASK)
class UserOpenRoomPerdayRecordSynTask extends AbstractDataSynTask<UserFangKaCost, UserOpenRoomPerdayRecord> {
	@Autowired
	private UserFangKaCostMapper userFangKaCostMapper;
	@Autowired
	private UserOpenRoomPerdayRecordMapper userOpenRoomPerdayRecordMapper;

	@Override
	protected List<UserOpenRoomPerdayRecord> dataScanForRun() {
		PageHelper.startPage(1, 1, FieldConstant.SortConstant.CREATE_TIME_DESC);
		List<UserOpenRoomPerdayRecord> list = this.userOpenRoomPerdayRecordMapper.get(new HashMap<>());
		String startDate = list.isEmpty() ? null : list.get(0).getCreateTime().toLocalDateTime().toLocalDate().toString();
		String endDate = LocalDate.now().minusDays(1).toString();
		return
				this.userFangKaCostMapper.findUserOpenRoomPerdayRecord(new HashMap<String, Object>() {{
					put(FieldConstant.CommonFieldConstant.startDate.name(), startDate);
					put(FieldConstant.CommonFieldConstant.endDate.name(), endDate);
				}});
	}

	@Override
	protected List<UserOpenRoomPerdayRecord> dataScanForTask() {
		String createDate = LocalDate.now().minusDays(1).toString();
		return
				this.userFangKaCostMapper.findUserOpenRoomPerdayRecord(new HashMap<String, Object>() {{
					put(FieldConstant.CommonFieldConstant.startDate.name(), createDate);
					put(FieldConstant.CommonFieldConstant.endDate.name(), createDate);
				}});
	}

	@Override
	protected void dataSyn(List<UserOpenRoomPerdayRecord> list) {
		if (dataSynBefore(list))
			this.userOpenRoomPerdayRecordMapper.saveMul(list);
	}

	@Override
	@Scheduled(cron = "0 0 3 * * ?")
	public void doTask() {
		super.doTask();
	}
}
