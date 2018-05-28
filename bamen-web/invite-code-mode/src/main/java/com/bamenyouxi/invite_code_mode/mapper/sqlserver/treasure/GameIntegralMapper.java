package com.bamenyouxi.invite_code_mode.mapper.sqlserver.treasure;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.invite_code_mode.model.sqlserver.treasure.GameIntegral;
import com.bamenyouxi.invite_code_mode.model.sqlserver.treasure.GameScoreInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface GameIntegralMapper extends CrudMapper<GameIntegral, Integer> {

    List<GameIntegral> getWithrawal(Map<String, Object> params);

    List<GameIntegral> getWithrawalOne(int gameId);

    int updateRMB(@Param("rmb")double rmb,@Param("userid") Integer userid);
}
