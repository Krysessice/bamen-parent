package com.bamenyouxi.invite_code_mode.mapper.sqlserver.record;

import com.bamenyouxi.core.impl.mapper.CrudMapper;

import com.bamenyouxi.invite_code_mode.model.sqlserver.record.RedeemCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Mapper
public interface RedeemCodeMapper extends CrudMapper<RedeemCode, Integer> {

    List<RedeemCode> queryExistence(String code);

    int saves(@Param("code") String code,@Param("card") Integer card,@Param("endtime") Timestamp endtime);

    List<RedeemCode> queryAllList(@Param("page") int page,@Param("size") int size,@Param("endDate")Object endDate,@Param("card")Object card);

    List<RedeemCode> queryPastNoAllList(@Param("page") int page,@Param("size") int size,@Param("endDate")Object endDate);
}
