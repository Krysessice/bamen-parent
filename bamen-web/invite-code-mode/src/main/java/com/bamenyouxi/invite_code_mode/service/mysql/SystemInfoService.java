package com.bamenyouxi.invite_code_mode.service.mysql;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.SystemInfoMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SystemInfo;
import com.bamenyouxi.invite_code_mode.util.RedisUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;

/**
 * 系统信息 service
 * Created by 13477 on 2017/7/10.
 */
@Service
@Order(SysConstant.CommandLineOrder.SYSTEM_INFO_INIT)
public class SystemInfoService extends AbstractCrudService<SystemInfo, Long> implements CommandLineRunner {
	@Autowired
	private SystemInfoMapper systemInfoMapper;
	@Autowired
	private RedisUtil redisUtil;

	@Override
	public CrudMapper<SystemInfo, Long> getMapper() {
		return systemInfoMapper;
	}

	@Override
	protected void saveBefore(SystemInfo systemInfo) {
		getMapper().update(new SystemInfo.Builder().sysFlag(false)._sysFlag(true).build());
		super.saveBefore(systemInfo);
	}

	@Override
	public void run(String... strings) throws Exception {
		RedisTemplate<String, SystemInfo> redisTemplate = redisUtil.getSystemInfoDB();
		if (redisTemplate.hasKey(SystemInfo.class.getName())) return;

		long count = PageHelper.count(
				() -> getMapper().get(new HashMap<String, Object>() {{
					put(FieldConstant.DBFieldConstant.F_SYS_FLAG.name(), SysConstant.SysFlagConstant.ENABLE);
				}})
		);
		if (count <= 0)
			Assert.isTrue(systemInfoMapper.systemInfoInit() > 0, TipMsgConstant.RUN_EXCEPTION);

		PageHelper.startPage(1, 1, FieldConstant.SortConstant.MODIFY_TIME_DESC);
		List<SystemInfo> list = getMapper().get(new HashMap<String, Object>() {{
			put(FieldConstant.DBFieldConstant.F_SYS_FLAG.name(), SysConstant.SysFlagConstant.ENABLE);
		}});
		Assert.notEmpty(list, TipMsgConstant.CONDITION_UNMET);

		redisUtil.saveSystemInfo(list.get(0));
	}
}
