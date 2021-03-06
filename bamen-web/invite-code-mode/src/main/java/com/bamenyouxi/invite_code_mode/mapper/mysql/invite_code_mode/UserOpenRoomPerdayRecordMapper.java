package com.bamenyouxi.invite_code_mode.mapper.mysql.invite_code_mode;

import com.bamenyouxi.core.impl.mapper.CrudMapper;

import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.UserOpenRoomPerdayRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * mapper for table: t_user_open_room_perday_record, model: {@link UserOpenRoomPerdayRecord}
 * Created by hc on 2017/7/9.
 */
@Mapper
public interface UserOpenRoomPerdayRecordMapper extends CrudMapper<UserOpenRoomPerdayRecord, Long> {


    List<UserOpenRoomPerdayRecord> getGameType(Map<String,Object> params);

}
