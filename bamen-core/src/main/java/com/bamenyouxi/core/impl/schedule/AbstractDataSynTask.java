package com.bamenyouxi.core.impl.schedule;

import java.util.List;

/**
 * 数据同步抽象类
 * <p>{@code SRC}   源数据类型</p>
 * <p>{@code DEST}  目标数据类型</p>
 * Created by 13477 on 2017/8/3.
 */
public abstract class AbstractDataSynTask<SRC, DEST> implements BaseScheduleTask {

	/**
	 * 初始化时数据扫描
	 * @return  List<DEST>
	 */
	protected abstract List<DEST> dataScanForRun();

	/**
	 * 定时任务时数据扫描
	 * @return  List<DEST>
	 */
	protected abstract List<DEST> dataScanForTask();

	protected boolean dataSynBefore(List<DEST> list) {
		return !(list == null || list.isEmpty());
	}

	/**
	 * 数据同步
	 * @param list  待同步数据集
	 */
	protected abstract void dataSyn(List<DEST> list);

	@Override
	public void run(String... strings) throws Exception {
		this.dataSyn(this.dataScanForRun());
	}

	@Override
	public void doTask() {
		this.dataSyn(this.dataScanForTask());
	}
}
