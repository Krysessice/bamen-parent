package com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.GameType;
import org.apache.ibatis.annotations.Mapper;

/**
 * mapper for table: t_game_type, model: {@link GameType}
 * Created by hc on 2017/7/9.
 */
@Mapper
public interface GameTypeMapper extends CrudMapper<GameType, Long> {

	int truncate();
}
