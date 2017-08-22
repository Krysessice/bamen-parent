package com.bamenyouxi.invite_code_mode.mapper.sqlserver.treasure;


import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.invite_code_mode.model.sqlserver.treasure.GameScoreLocker;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GameScoreLockerMapper extends CrudMapper<GameScoreLocker,Integer> {

}
