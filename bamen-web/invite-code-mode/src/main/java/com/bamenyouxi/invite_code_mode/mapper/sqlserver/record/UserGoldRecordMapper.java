package com.bamenyouxi.invite_code_mode.mapper.sqlserver.record;

import com.bamenyouxi.core.impl.mapper.CrudMapper;

import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.UserGold;
import com.bamenyouxi.invite_code_mode.model.sqlserver.record.UserGoldRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserGoldRecordMapper extends CrudMapper<UserGoldRecord, Long> {

    /**
     * 转换UserGoldRecord为UserGold输出
     * @param params    条件
     * @return  List<UserGold>
     */
    List<UserGold> getUserGold(Map<String, Object> params);
}
