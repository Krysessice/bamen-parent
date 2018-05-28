package com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.PayGoldRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PayGoldRecordMapper extends CrudMapper<PayGoldRecord,Long> {

    List<PayGoldRecord> selectedName(Map<String, Object> params);

}
