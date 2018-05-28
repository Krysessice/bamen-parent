package com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.PayOrderPerdayStatistic;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.UserGoldPerdayStatistic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * mapper for table: t_user_gold_perday_statistic, model: {@link UserGoldPerdayStatistic}
 * Created by hc on 2018/4/26.
 */
@Mapper
public interface UserGoldPerdayStatisticMapper extends CrudMapper<UserGoldPerdayStatistic, Long> {

	@Select("SELECT ifnull(sum(F_SystemCost), 0) AS systemCost FROM t_user_gold_perday_statistic")
	UserGoldPerdayStatistic sumTotal();

	List<UserGoldPerdayStatistic> userGoldPerdayStatistic(@Param("createTime") String createTime);
}
