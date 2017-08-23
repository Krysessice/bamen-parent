package com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.SystemInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SystemInfoMapper extends CrudMapper<SystemInfo, Long> {

	@Insert("INSERT INTO t_system_info VALUES()")
	int systemInfoInit();
}