package com.bamenyouxi.cow_nn_mode.mapper.sqlserver.treasure;


import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.cow_nn_mode.model.sqlserver.treasure.GameScoreInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * mapper for QPTreasureDB.dbo.OnLineOrder
 * Created by hc on 2017/7/9.
 */
@Mapper
public interface GameScoreInfoMapper extends CrudMapper<GameScoreInfo, Integer> {


    int updateCardNum(GameScoreInfo gameScoreInfo);
}
