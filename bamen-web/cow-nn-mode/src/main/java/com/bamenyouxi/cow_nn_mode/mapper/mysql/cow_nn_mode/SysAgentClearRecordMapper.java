package com.bamenyouxi.cow_nn_mode.mapper.mysql.cow_nn_mode;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.SysAgentClearRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * mapper for table: t_sys_agent_clear_record, model: {@link SysAgentClearRecord}
 * Created by hc on 2017/7/9.
 */
@Mapper
public interface SysAgentClearRecordMapper extends CrudMapper<SysAgentClearRecord, Long> {

    List<SysAgentClearRecord> gets();
}
