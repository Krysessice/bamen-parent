package com.bamenyouxi.core.impl.schedule;

import com.bamenyouxi.core.constant.SysConstant;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * ScheduleTask top interface
 * Created by 13477 on 2017/6/21.
 */
public interface BaseScheduleTask extends CommandLineRunner {

	/**
	 * 执行任务
	 */
	@Scheduled(initialDelay = SysConstant.ScheduleTask.DEFAULT_DELAY, fixedDelay = SysConstant.ScheduleTask.DEFAULT_INTERVAL)
	void doTask();
}
