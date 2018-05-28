package com.bamenyouxi.invite_code_mode.mapper.sqlserver.treasure;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.invite_code_mode.model.sqlserver.treasure.GameScoreInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * mapper for QPTreasureDB.dbo.OnLineOrder
 * Created by hc on 2017/7/9.
 */
@Mapper
public interface GameScoreInfoMapper extends CrudMapper<GameScoreInfo, Integer> {

	int updateCardNum(GameScoreInfo record);
	int updateGoldNum(@Param("gameId") int presentee,@Param("score") int goldNum);

	List<GameScoreInfo> queryUserGoldScore(Map<String,Object> params);

	List<GameScoreInfo> queryUserGold(Integer gameId);

	int updateUserGoldSocre(@Param("score") Object score, @Param("userId") Integer userId);

	int addUserGoldSocre(@Param("score") Object score, @Param("userId") Integer userId);
}
