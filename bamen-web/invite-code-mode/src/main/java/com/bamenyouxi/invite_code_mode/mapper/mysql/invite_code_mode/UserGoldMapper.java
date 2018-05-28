package com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode;


import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.TeamPayOrder;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.UserGold;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserGoldMapper extends CrudMapper<UserGold, Long> {


    List<UserGold> sumActual(@Param("params") Map<String, Object> params);

    List<UserGold> gets(@Param("params") Map<String, Object> params);

    UserGold queryGoldMyself( Map<String, Object> params);

}
