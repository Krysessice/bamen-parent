package com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.CleanGoldRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CleanGoldRecordMapper extends CrudMapper<CleanGoldRecord, Long> {

    int saveScore(CleanGoldRecord goldRecord);

    List<CleanGoldRecord> getCleanGoldAll(Map<String ,Object> params);
}
