package com.bamenyouxi.invite_code_mode.util;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode.SysResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;

/**
 * 用户资源工具类
 * Created by 13477 on 2017/8/16.
 */
@Component
public final class SysResourceUtil {

	@Autowired
	private SysResourceMapper sysResourceMapper;

	/**
	 * 判断用户资源
	 * @param userId        资源用户id
	 * @param resourceId    子用户id
	 * @param resourceName  资源名
	 */
	public void exists(Long userId, Long resourceId, String resourceName) {
		Assert.notEmpty(
				this.sysResourceMapper.get(new HashMap<String, Object>() {{
					put(FieldConstant.DBFieldConstant.F_USER_ID.name(), userId);
					put(FieldConstant.DBFieldConstant.F_RESOURCE_ID.name(), resourceId);
					put(FieldConstant.DBFieldConstant.F_RESOURCE_NAME.name(), resourceName);
					put(FieldConstant.DBFieldConstant.F_SYS_FLAG.name(), SysConstant.SysFlagConstant.ENABLE);
				}}),
				TipMsgConstant.ACCESS_TO_RESOURCES_WITHOUT_AUTHORITY
		);
	}

	/**
	 * 根据gameId判断用户资源
	 * @param gameId1       资源用户gameId
	 * @param gameId2       子用户gameId
	 * @param resourceName  资源名
	 */
	public void exists(Integer gameId1, Integer gameId2, String resourceName) {
		Assert.notEmpty(
				this.sysResourceMapper.get(new HashMap<String, Object>() {{
					put(FieldConstant.CommonFieldConstant.gameId1.name(), gameId1);
					put(FieldConstant.CommonFieldConstant.gameId2.name(), gameId2);
					put(FieldConstant.DBFieldConstant.F_RESOURCE_NAME.name(), resourceName);
					put(FieldConstant.DBFieldConstant.F_SYS_FLAG.name(), SysConstant.SysFlagConstant.ENABLE);
				}}),
				TipMsgConstant.ACCESS_TO_RESOURCES_WITHOUT_AUTHORITY
		);
	}
}
