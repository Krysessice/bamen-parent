package com.bamenyouxi.invite_code_mode.schedule.other_task;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.impl.schedule.BaseScheduleTask;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.SysAgentMapper;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.account.AccountsInfoMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SysAgent;
import com.bamenyouxi.invite_code_mode.model.sqlserver.account.AccountsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户昵称校验及统一
 * Created by 13477 on 2017/8/3.
 */
@Component
@Order(SysConstant.CommandLineOrder.SYS_AGENT_NICK_NAME_SYN_TASK)
class SysAgentNickNameSynTask implements BaseScheduleTask {
	private final static int NICK_NAME_CHAR_LENGTH = 25;

	@Autowired
	private SysAgentMapper sysAgentMapper;
	@Autowired
	private AccountsInfoMapper accountsInfoMapper;

	@Override
	@Scheduled(cron = "0 0 3 * * ?")
	public void doTask() {
		this.syn();
	}

	@Override
	public void run(String... strings) throws Exception {
		this.syn();
	}

	private void syn() {
		this.sysAgentMapper.get(new HashMap<String, Object>() {{
			put(FieldConstant.CommonFieldConstant.nickNameCharLength.name(), NICK_NAME_CHAR_LENGTH);
		}}).forEach(sysAgent -> {
			List<AccountsInfo> list;
			Map<String, Object> params = new HashMap<>();
			{
				params.put(FieldConstant.DBFieldConstant.GameID.name(), sysAgent.getGameId());
				list = this.accountsInfoMapper.get(params);
				if (list.isEmpty()) return;
				String nickName = list.get(0).getNickName();
				if (nickName.length() > NICK_NAME_CHAR_LENGTH) return;
				this.sysAgentMapper.update(new SysAgent.Builder().id(sysAgent.getId()).nickName(nickName).build());
			}
		});

	}
}
