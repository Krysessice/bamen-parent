package com.bamenyouxi.cow_nn_mode.mapper.mysql.cow_nn_mode;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.PayOrder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
public interface PayOrderMapper extends CrudMapper<PayOrder, Long> {
    List<PayOrder> getPayAgent(Map<String,Object> params);
    List<PayOrder> getPayDay(Map<String,Object> params);
    List<PayOrder> getAgentPayList(Map<String,Object> params);
}
