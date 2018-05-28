package com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.PayOrder;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.SysAgentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * mapper for table: t_pay_order, model: {@link PayOrder}
 * Created by hc on 2017/7/9.
 */
@Mapper
public interface PayOrderMapper extends CrudMapper<PayOrder, Long> {


    PayOrder queryMyself(Map<String, Object> params);
}
