package com.bamenyouxi.invite_code_mode.test.mapper;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.treasure.UserFangKaSendMapper;
import com.github.pagehelper.PageHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

/**
 * test for UserFangKaSendMapper
 * Created by 13477 on 2017/8/21.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class UserFangKaSendMapperTest {
	@Autowired
	private UserFangKaSendMapper userFangKaSendMapper;

	@Test
	public void testGet() {
//		System.out.println(PageHelper.count(() -> userFangKaSendMapper.get(new HashMap<>())));
		PageHelper.startPage(1, 10, FieldConstant.SortConstant.SEND_TIME_DESC);
		userFangKaSendMapper.get(new HashMap<>()).forEach(System.out::println);
	}
}
