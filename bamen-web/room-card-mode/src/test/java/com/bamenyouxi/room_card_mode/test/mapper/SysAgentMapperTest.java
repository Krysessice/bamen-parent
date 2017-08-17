package com.bamenyouxi.room_card_mode.test.mapper;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode.SysAgentMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

/**
 * SysAgentMapperTest
 * Created by 13477 on 2017/8/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysAgentMapperTest {
	@Autowired
	private SysAgentMapper sysAgentMapper;

	@Test
	public void testGet() {
		sysAgentMapper.get(new HashMap<String, Object>() {{
			put(FieldConstant.DBFieldConstant.F_ID.name(), 1);
		}});
	}
}
