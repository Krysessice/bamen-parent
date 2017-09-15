package com.bamenyouxi.core.constant;

import java.time.LocalDate;

/**
 * 系统常量
 * Created by 13477 on 2017/6/19.
 */
public class SysConstant {

	/**
	 * sysFlag
	 */
	public static class SysFlagConstant {
		public static final int ENABLE = 1;     //可用
		public static final int DISABLE = 0;    //禁用
		public static final int MINUS = -1;     //减少
	}

	public static class SqlserverFlagConstant {
		public static final int SUCCESS = 0;
		public static final int ERROR_1 = 1;
	}

	/**
	 * 分页常量类
	 */
	public static class PageConstant {
		public static final String DEFAULT_PAGE_NAME = "page";
		public static final String DEFAULT_SIZE_NAME = "size";

		public static final int DEFAULT_PAGE = 1;
		public static final int DEFAULT_SIZE = 10;
	}

	/**
	 * 用户资源常量类
	 */
	public static class SysResourceConstant {
		public static final String RESOURCE_USER = "USER"; //用户资源
		public static final String RESOURCE_PAY_ORDER = "PAY_ORDER"; //订单资源
	}

	/**
	 * jsonFormat
	 */
	public static class JsonFormat {
		public static final String DEFAULT_LOCAL = "zh";
		public static final String DEFAULT_TIMEZONE = "GMT+8";
		public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
		public static final String DATE_PATTERN = "yyyy-MM-dd";
		public static final String TIME_PATTERN = "HH:mm:ss";
		public static final String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
	}

	/**
	 * 定时任务
	 */
	public static class ScheduleTask {
		public static final int DEFAULT_DELAY = 5000 * 60;          //默认延迟时间
		public static final int DEFAULT_INTERVAL = 5000 * 60;       //默认间隔时间

		public static final int SYS_AGENT_SYN_DELAY = 5000 * 60;    //用户数据同步延迟时间
		public static final int SYS_AGENT_SYN_INTERVAL = 5000 * 60; //用户数据同步间隔时间

		public static final int PAY_ORDER_SYN_DELAY = 5000 * 60;            //订单数据同步延迟时间
		public static final int PAY_ORDER_SYN_INTERVAL = 1000 * 60 * 30;    //订单数据同步间隔时间

		public static final LocalDate BASE_PAY_ORDER_STATISTIC_DATE = LocalDate.parse("2017-06-01"); //订单基准时间
	}

	public static class CommandLineOrder {
		public static final int SYSTEM_INFO_INIT = 1;                       // 系统信息初始化
		public static final int SYS_AGENT_DATA_SYN_TASK = 2;                // 用户数据同步
		public static final int PAY_ORDER_DATA_SYN_TASK = 3;                // 订单数据同步
		public static final int OPEN_ROOM_PERHOUR_RECORD_TASk = 4;			//每时房卡消耗
		public static final int PAY_ORDER_PERDAY_STATISTIC_TASK = 5;        // 每日订单统计
		public static final int USER_OPEN_ROOM_PERDAY_RECORD_SYN_TASK = 6;  // 每日房卡消耗
		public static final int OMIT_PAY_ORDER_DATA_SYN_TASK = 7;           // 遗漏订单数据同步
		public static final int RECRUIT_NUM_SYN_TASK = 8;                   // 代理招募人数校验
		public static final int SYS_AGENT_CLEAR_RECORD_SERVICE_RUNNER = 9;
		public static final int RESET_SHOW_ANNOUNCE = 10;                    // 重置代理公告显示
		public static final int GAME_TYPE_SYN_TASK = 11;                    // 游戏类型同步
		public static final int SYS_AGENT_NICK_NAME_SYN_TASK = 12;          // 用户昵称校验与统一
	}

	/**
	 * 分成比例
	 * Deprecated
	 */
	@Deprecated
	public static class Commission {
		public static final double t1 = 0.4;
		public static final double t2 = 0.08;
		public static final double t3 = 0.05;
	}
}
