package com.bamenyouxi.cow_nn_mode.mapper.mysql.cow_nn_mode;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.Withdraw;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface WithdrawMapper  extends CrudMapper<Withdraw, Long> {

    Withdraw getWithdraw(Map<String,Object> params);
    int add(Withdraw withdraw);
    List<Withdraw> gets(Map<String,Object> params);
}
