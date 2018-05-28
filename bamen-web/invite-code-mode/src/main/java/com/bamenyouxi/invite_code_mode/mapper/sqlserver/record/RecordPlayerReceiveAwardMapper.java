package com.bamenyouxi.invite_code_mode.mapper.sqlserver.record;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.invite_code_mode.model.sqlserver.record.RecordPlayerReceiveAward;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface RecordPlayerReceiveAwardMapper extends CrudMapper<RecordPlayerReceiveAward, Integer> {

    int addRMB(@Param("userid") int userid,@Param("rmb") double rmb);

    List<RecordPlayerReceiveAward> getCashRecord(Map<String,Object> params);
}
