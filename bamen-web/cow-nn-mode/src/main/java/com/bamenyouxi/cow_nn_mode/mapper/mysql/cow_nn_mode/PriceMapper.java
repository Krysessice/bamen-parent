package com.bamenyouxi.cow_nn_mode.mapper.mysql.cow_nn_mode;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.Price;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PriceMapper extends CrudMapper<Price, Long> {
}
