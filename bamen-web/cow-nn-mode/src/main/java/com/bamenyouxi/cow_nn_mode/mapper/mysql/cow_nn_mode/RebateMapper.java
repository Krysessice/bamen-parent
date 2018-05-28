package com.bamenyouxi.cow_nn_mode.mapper.mysql.cow_nn_mode;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.Rebate;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.SysAgent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Mapper
public interface RebateMapper extends CrudMapper<Rebate, Long> {

    int saves(@Param("orderNo") String orderNo,
              @Param("account")String account,
              @Param("nickName")String nickName,
              @Param("payPrice")String payPrice,
              @Param("rebateRate") BigDecimal rebateRate,
              @Param("rebateMoney") BigDecimal rebateMoney,
              @Param("favorerAccount") String favorerAccount,
              @Param("favorerRoleId") Integer favorerRoleId);


    List<Rebate> getDetail(Map<String,Object> params);
    Rebate getDetailSum(Map<String,Object> params);
}
