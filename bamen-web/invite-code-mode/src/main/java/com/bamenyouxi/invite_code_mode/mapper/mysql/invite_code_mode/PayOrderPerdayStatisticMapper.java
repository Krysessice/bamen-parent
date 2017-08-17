package com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.PayOrderPerdayStatistic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * mapper for table: t_pay_order_perday_statistic, model: {@link PayOrderPerdayStatistic}
 * Created by hc on 2017/7/9.
 */
@Mapper
public interface PayOrderPerdayStatisticMapper extends CrudMapper<PayOrderPerdayStatistic, Long> {

	@Select("select sum(F_PAY_PRICE) as payPrice, sum(F_CARD_GOLD) as cardGold from t_pay_order_perday_statistic")
	PayOrderPerdayStatistic sumTotal();

	List<PayOrderPerdayStatistic> payOrderPerdayStatistic(@Param("createTime") String createTime);
}
