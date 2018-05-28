package com.bamenyouxi.invite_code_mode.mapper.sqlserver.record;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.invite_code_mode.model.sqlserver.record.RiTurntableAwardList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface RiTurntableAwardListMapper extends CrudMapper<RiTurntableAwardList, Integer> {

    List<RiTurntableAwardList> getDaily(Map<String,Object> params);

    List<RiTurntableAwardList> queryProbabilitySum();

    RiTurntableAwardList getTurnAwardProbability(Object turnawardno);

    int updateProbability(@Param("turnawardtype")int turnawardtype,@Param("turnawardamount")double turnawardamount,
                          @Param("turnawardprobability")double turnawardprobability,@Param("turnawardno")int turnawardno);
}
