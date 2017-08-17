package com.bamenyouxi.invite_code_mode.mapper.sqlserver.platform;

import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.GameType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * mapper for QPTreasureDB.dbo.GameKindItem
 * Created by hc on 2017/7/9.
 */
@Mapper
public interface GameKindItemMapper {

	@Select("select KindID gameTypeId, KindName gameName from GameKindItem")
	List<GameType> listAsGameType();
}
