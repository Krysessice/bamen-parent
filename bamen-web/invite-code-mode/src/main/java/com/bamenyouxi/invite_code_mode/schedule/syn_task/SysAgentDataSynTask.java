package com.bamenyouxi.invite_code_mode.schedule.syn_task;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.impl.schedule.AbstractDataSynTask;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.SysAgentMapper;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.SysResourceMapper;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.account.AccountsInfoMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SysAgent;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SysResource;
import com.bamenyouxi.invite_code_mode.model.sqlserver.account.AccountsInfo;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;

/**
 * 用户数据同步
 * Created by 13477 on 2017/8/3.
 */
@Component
@Order(SysConstant.CommandLineOrder.SYS_AGENT_DATA_SYN_TASK)
class SysAgentDataSynTask extends AbstractDataSynTask<AccountsInfo, SysAgent> {
	private static Timestamp createTime;

	@Autowired
	private SysAgentMapper sysAgentMapper;
	@Autowired
	private AccountsInfoMapper accountsInfoMapper;
	@Autowired
	private SysResourceMapper sysResourceMapper;

	@Autowired
	private SysAgentInviteCodeSynTask sysAgentInviteCodeSynTask;

	@Override
	protected List<SysAgent> dataScanForRun() {
		createTime = Timestamp.from(Instant.now());
		PageHelper.startPage(1, 1, FieldConstant.SortConstant.CREATE_TIME_DESC);
		List<SysAgent> list = sysAgentMapper.get(new HashMap<>());
		Timestamp temp = list.isEmpty() ? null : list.get(0).getCreateTime();
		return
				accountsInfoMapper.getOfSysAgent(new HashMap<String, Object>() {{
					put(FieldConstant.CommonFieldConstant.startTime.name(), temp);
				}});
	}

	@Override
	protected List<SysAgent> dataScanForTask() {
		List<SysAgent> sysAgents = accountsInfoMapper.getOfSysAgent(new HashMap<String, Object>() {{
			put(FieldConstant.CommonFieldConstant.startTime.name(), createTime);
		}});
		createTime = Timestamp.from(Instant.now());
		return sysAgents;
	}

	@Override
	protected void dataSyn(List<SysAgent> list) {
		if (super.dataSynBefore(list))
			list.forEach(this::dataSyn);
	}

	@Transactional
	private void dataSyn(SysAgent sysAgent) {
		SysAgent.Builder.defaultPwdInject(sysAgent);
		this.sysAgentMapper.save(sysAgent);
		this.sysResourceMapper.save(SysResource.Builder.selfSysResource(sysAgent.getId(), SysConstant.SysResourceConstant.RESOURCE_USER));
		this.sysResourceMapper.save(SysResource.Builder.selfSysResource(sysAgent.getId(), SysConstant.SysResourceConstant.RESOURCE_PAY_ORDER));
	}

	@Override
	public void run(String... strings) throws Exception {
		super.run(strings);
		this.sysAgentInviteCodeSynTask.dataSyn(this.sysAgentInviteCodeSynTask.dataScanForRun());
	}

	@Override
	@Scheduled(initialDelay = SysConstant.ScheduleTask.SYS_AGENT_SYN_DELAY, fixedDelay = SysConstant.ScheduleTask.SYS_AGENT_SYN_INTERVAL)
	public void doTask() {
		super.doTask();
		this.sysAgentInviteCodeSynTask.dataSyn(this.sysAgentInviteCodeSynTask.dataScanForTask());
	}
}
