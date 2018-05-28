package com.bamenyouxi.invite_code_mode.mapper.sqlserver.record;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.model.mysql.BaseEntity;
import com.bamenyouxi.invite_code_mode.model.sqlserver.record.UseridScoreRoomnumSave;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Mapper
public interface UseridScoreRoomnumSaveMapper extends CrudMapper<UseridScoreRoomnumSave, Integer> {

    List<UseridScoreRoomnumSave> getUser(Map<String,Object> params);

    List<UseridScoreRoomnumSave> getUserScore(Map<String,Object> params);

    List<UseridScoreRoomnumSave> countKind(Map<String,Object> params);

    List<UseridScoreRoomnumSave> queryAllRoom(Map<String,Object> params);

    List<UseridScoreRoomnumSave> queryPartnerAgent(Map<String,Object> params);

    UseridScoreRoomnumSave querySumPartnerAgent(Map<String,Object> params);
}
