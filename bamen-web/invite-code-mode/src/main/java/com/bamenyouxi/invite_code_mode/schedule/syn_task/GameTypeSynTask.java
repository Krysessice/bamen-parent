package com.bamenyouxi.invite_code_mode.schedule.syn_task;

import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.impl.schedule.BaseScheduleTask;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.GameTypeMapper;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.platform.GameKindItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * task for t_game_type data syn
 * Created by 13477 on 2017/8/2.
 */
@Component
@Order(SysConstant.CommandLineOrder.GAME_TYPE_SYN_TASK)
class GameTypeSynTask implements BaseScheduleTask {
	@Autowired
	private GameTypeMapper gameTypeMapper;
	@Autowired
	private GameKindItemMapper gameKindItemMapper;

	@Override
	@Scheduled(cron = "0 0 3 * * ?")
	public void doTask() {
		this.gameTypeRefresh();
	}

	@Override
	public void run(String... strings) throws Exception {
		this.gameTypeRefresh();
	}

	private void gameTypeRefresh() {
		this.gameTypeMapper.truncate();
		this.gameTypeMapper.saveMul(this.gameKindItemMapper.listAsGameType());
	}
}
