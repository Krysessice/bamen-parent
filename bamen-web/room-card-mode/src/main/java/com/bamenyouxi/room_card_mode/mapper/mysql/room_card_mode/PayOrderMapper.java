package com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.PayOrder;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.SysAgent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

/**
 * mapper for table: t_pay_order, model: {@link PayOrder}
 * Created by 13477 on 2017/8/23.
 */
@Mapper
public interface PayOrderMapper extends CrudMapper<PayOrder, Long> {

    int save(PayOrder payOrder);



}
