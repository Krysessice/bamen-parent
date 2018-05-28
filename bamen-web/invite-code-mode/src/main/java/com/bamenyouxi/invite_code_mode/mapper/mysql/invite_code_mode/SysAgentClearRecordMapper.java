package com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SysAgentClearRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * mapper for table: t_sys_agent_clear_record, model: {@link SysAgentClearRecord}
 * Created by hc on 2017/7/9.
 */
@Mapper
public interface SysAgentClearRecordMapper extends CrudMapper<SysAgentClearRecord, Long> {

}
