package com.bamenyouxi.invite_code_mode.mapper.sqlserver.treasure;

import com.bamenyouxi.invite_code_mode.model.sqlserver.treasure.CardLineOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CardLineOrderMapper {

    List<CardLineOrder> getWxPay(Map<String,Object> params);
}
