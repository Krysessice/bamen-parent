package com.bamenyouxi.invite_code_mode.mapper.sqlserver.treasure;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.PayOrder;
import com.bamenyouxi.invite_code_mode.model.sqlserver.treasure.OnLineOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * mapper for QPTreasureDB.dbo.OnLineOrder
 * Created by hc on 2017/7/9.
 */
@Mapper
public interface OnLineOrderMapper extends CrudMapper<OnLineOrder, Long> {

	/**
	 * 转换OnLineOrder为PayOrder输出
	 * @param params    条件
	 * @return  List<PayOrder>
	 */
	List<PayOrder> getOfPayOrder(Map<String, Object> params);
}
