package com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SystemInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * mapper for table: t_system_info, model: {@link SystemInfo}
 * Created by hc on 2017/7/9.
 */
@Mapper
public interface SystemInfoMapper extends CrudMapper<SystemInfo, Long> {

}
