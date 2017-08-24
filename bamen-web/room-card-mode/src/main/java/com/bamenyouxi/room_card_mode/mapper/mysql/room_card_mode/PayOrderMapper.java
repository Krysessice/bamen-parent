package com.bamenyouxi.room_card_mode.mapper.mysql.room_card_mode;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.PayOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * mapper for table: t_pay_order, model: {@link PayOrder}
 * Created by 13477 on 2017/8/23.
 */
@Mapper
public interface PayOrderMapper extends CrudMapper<PayOrder, Long> {
}
