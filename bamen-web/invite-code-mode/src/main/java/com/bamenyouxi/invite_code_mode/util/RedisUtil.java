package com.bamenyouxi.invite_code_mode.util;

import com.bamenyouxi.core.constant.RedisConstant;
import com.bamenyouxi.core.util.AbstractRedisUtil;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.GoldInfo;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SystemInfo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis 工具类
 * Created by 13477 on 2017/8/16.
 */
@Component
public final class RedisUtil extends AbstractRedisUtil {

	/**
	 * 获取存储系统信息的数据库引擎
	 * @return  RedisTemplate
	 */
	public RedisTemplate<String, SystemInfo> getSystemInfoDB() {
		return super.getInstanceN(RedisConstant.DataBase.SYSTEM_INFO_DB);
	}

	/**
	 * 存储/更新系统信息
	 * @param systemInfo    待存储的系统信息
	 */
	public void saveSystemInfo(SystemInfo systemInfo) {
		getSystemInfoDB().opsForValue().set(SystemInfo.class.getName(), systemInfo);
	}

	/**
	 * 从redis中获取系统信息
	 * @return  SystemInfo
	 */
	public SystemInfo getSystemInfo() {
		return getSystemInfoDB().opsForValue().get(SystemInfo.class.getName());
	}




	/**
	 * 获取存储系统信息的数据库引擎
	 * @return  RedisTemplate
	 */
	public RedisTemplate<String, GoldInfo> getGoldInfoDB() {
		return super.getInstanceN(RedisConstant.DataBase.GOLD_INFO_DB);
	}

	/**
	 * 存储/更新系统信息
	 * @param goldInfo    待存储的系统信息
	 */
	public void saveGoldInfo(GoldInfo goldInfo) {
		getGoldInfoDB().opsForValue().set(GoldInfo.class.getName(), goldInfo);
	}

	/**
	 * 从redis中获取系统信息
	 * @return  GoldInfo
	 */
	public GoldInfo getGoldInfo() {
		return getGoldInfoDB().opsForValue().get(GoldInfo.class.getName());
	}
}
