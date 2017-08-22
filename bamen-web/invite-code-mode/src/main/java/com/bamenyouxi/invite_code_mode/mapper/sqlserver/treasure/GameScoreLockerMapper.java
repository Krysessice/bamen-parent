package com.bamenyouxi.invite_code_mode.mapper.sqlserver.treasure;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.invite_code_mode.model.sqlserver.treasure.GameScoreLocker;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GameScoreLockerMapper extends CrudMapper<GameScoreLocker,Integer> {

}
