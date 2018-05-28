package com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SysAgentClearRecord;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SysGoldClearRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * mapper for table: t_sys_gold_clear_record, model: {@link SysGoldClearRecord}
 * Created by hc on 2018/4/27.
 */
@Mapper
public interface SysGoldClearRecordMapper extends CrudMapper<SysGoldClearRecord, Long> {

    List<SysGoldClearRecord> gets();
}
