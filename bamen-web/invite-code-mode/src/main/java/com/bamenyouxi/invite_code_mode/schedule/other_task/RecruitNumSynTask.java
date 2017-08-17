package com.bamenyouxi.invite_code_mode.schedule.other_task;

import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.impl.schedule.BaseScheduleTask;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.SysAgentMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SysAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 招募人数同步
 * <p>每天定时将招募人数进行统一，防止出现混乱</p>
 * Created by 13477 on 2017/6/22.
 */
@Component
@Order(SysConstant.CommandLineOrder.RECRUIT_NUM_SYN_TASK)
class RecruitNumSynTask implements BaseScheduleTask {

	@Autowired
	private SysAgentMapper sysAgentMapper;

	private void syn() {
		sysAgentMapper.findAgentRecruitNum().forEach(sysAgent ->
			sysAgentMapper.update(new SysAgent.Builder()._gameId(sysAgent.getGameId()).recruitNum(sysAgent.getRecruitNum()).build())
		);
	}

	@Override
	public void run(String... args) throws Exception {
		this.syn();
	}

	@Override
	@Scheduled(cron = "0 5 0 * * ?")
	public void doTask() {
		this.syn();
	}

}
