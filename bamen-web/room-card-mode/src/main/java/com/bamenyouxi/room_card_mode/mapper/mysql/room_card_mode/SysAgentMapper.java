package com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.SysAgent;
import org.apache.ibatis.annotations.Mapper;

/**
 * mapper for table: t_sys_agent, model: {@link SysAgent}
 * Created by hc on 2017/7/9.
 */
@Mapper
public interface SysAgentMapper extends CrudMapper<SysAgent, Long> {

}
