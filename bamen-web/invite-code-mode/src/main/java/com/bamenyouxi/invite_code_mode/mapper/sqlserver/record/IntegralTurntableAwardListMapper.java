package com.bamenyouxi.invite_code_mode.mapper.sqlserver.record;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.invite_code_mode.model.sqlserver.record.IntegralTurntableAwardList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface IntegralTurntableAwardListMapper extends CrudMapper<IntegralTurntableAwardList, Integer> {

    List<IntegralTurntableAwardList> getIntegral(Map<String, Object> params);

    int updateIntegral(@Param("turnawardamount") double turnawardamount,@Param("turnawardgoodamcout") double turnawardgoodamcout,
                       @Param("turnawardno") int turnawardno);
}
