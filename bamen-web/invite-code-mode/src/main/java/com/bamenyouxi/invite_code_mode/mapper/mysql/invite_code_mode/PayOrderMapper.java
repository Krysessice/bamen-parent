package com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.PayOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * mapper for table: t_pay_order, model: {@link PayOrder}
 * Created by hc on 2017/7/9.
 */
@Mapper
public interface PayOrderMapper extends CrudMapper<PayOrder, Long> {

}
