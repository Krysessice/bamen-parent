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
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;

/**
 * 用户邀请码同步
 * Created by 13477 on 2017/8/3.
 */
@Component
class SysAgentInviteCodeSynTask extends AbstractDataSynTask<AccountsInfo, SysAgent> {
	private static Timestamp transMemberTime;

	@Autowired
	private SysAgentMapper sysAgentMapper;
	@Autowired
	private AccountsInfoMapper accountsInfoMapper;
	@Autowired
	private SysResourceMapper sysResourceMapper;

	@Override
	public List<SysAgent> dataScanForRun() {
		transMemberTime = Timestamp.from(Instant.now());
		PageHelper.startPage(1, 1, FieldConstant.SortConstant.TRANS_MEMBER_TIME_DESC);
		List<SysAgent> list = this.sysAgentMapper.get(new HashMap<>());
		return
				(list.isEmpty() || list.get(0).getTransMemberTime() == null)
				?
				this.accountsInfoMapper.findBySuperAgentTimeNotNullOfSysAgent()
				:
				this.accountsInfoMapper.getBySuperAgentTimeBetweenOfSysAgent(new HashMap<String, Object>() {{
					put(FieldConstant.CommonFieldConstant.startTime.name(), list.get(0).getTransMemberTime());
				}});
	}

	@Override
	public List<SysAgent> dataScanForTask() {
		List<SysAgent> sysAgents = this.accountsInfoMapper.getBySuperAgentTimeBetweenOfSysAgent(new HashMap<String, Object>() {{
			put(FieldConstant.CommonFieldConstant.startTime.name(), transMemberTime);
		}});
		transMemberTime = Timestamp.from(Instant.now());
		return sysAgents;
	}

	@Override
	public void dataSyn(List<SysAgent> list) {
		if (super.dataSynBefore(list))
			list.forEach(this::dataSyn);
	}

	@Transactional
	private void dataSyn(SysAgent sysAgent) {
		SysAgent.Builder._gameIdInject(sysAgent);
		this.sysAgentMapper.update(sysAgent);
		this.sysAgentMapper.update(
				new SysAgent.Builder()
						._gameId(sysAgent.getSuperAgentGameId())
						.recruitNumInt(SysConstant.SysFlagConstant.ENABLE)
						.build()
		);
		this.sysResourceMapper.save(
				SysResource.Builder
						.gameIdSysResource(sysAgent.getSuperAgentGameId(), sysAgent.getGameId(), SysConstant.SysResourceConstant.RESOURCE_USER)
		);
		this.sysResourceMapper.save(
				SysResource.Builder
						.gameIdSysResource(sysAgent.getSuperAgentGameId(), sysAgent.getGameId(), SysConstant.SysResourceConstant.RESOURCE_PAY_ORDER)
		);
	}

	@Override
	public void run(String... strings) throws Exception {}

	@Override
	public void doTask() {}
}
