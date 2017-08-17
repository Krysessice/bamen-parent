package com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.TeamPayOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * mapper for table: null, model: {@link TeamPayOrder}
 * Created by hc on 2017/7/9.
 */
@Mapper
public interface TeamPayOrderMapper extends CrudMapper<TeamPayOrder, Long> {

	/**
	 * 团队充值实时统计
	 * @param params 参数
	 * @return  List
	 */
	List<TeamPayOrder> sumActual(@Param("params") Map<String, Object> params);

	/**
	 * 团队充值日统计
	 * @param params 参数
	 * @return  List
	 */
	List<TeamPayOrder> sumDaily(@Param("params") Map<String, Object> params);
}
