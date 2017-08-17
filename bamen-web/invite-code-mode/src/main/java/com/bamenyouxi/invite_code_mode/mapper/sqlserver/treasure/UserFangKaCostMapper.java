package com.bamenyouxi.invite_code_mode.mapper.sqlserver.treasure;

import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.OpenRoomPerhourRecord;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.UserOpenRoomPerdayRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * mapper for QPTreasureDB.dbo.UserFangKaCost
 * Created by 13477 on 2017/8/2.
 */
@Mapper
public interface UserFangKaCostMapper {

	List<OpenRoomPerhourRecord> findOpenRoomPerhourRecord();

	List<UserOpenRoomPerdayRecord> findUserOpenRoomPerdayRecord(Map<String, Object> params);
}
