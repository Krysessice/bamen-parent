package com.bamenyouxi.invite_code_mode.mapper.sqlserver.record;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.invite_code_mode.model.sqlserver.record.TurntableAwardType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TurntableAwardTypeMapper  extends CrudMapper<TurntableAwardType, Integer> {

    List<TurntableAwardType> getTurntableAwardType(Map<String, Object> params);
}
