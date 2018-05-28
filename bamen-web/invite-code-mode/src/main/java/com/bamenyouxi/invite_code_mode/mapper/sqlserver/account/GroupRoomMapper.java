package com.bamenyouxi.invite_code_mode.mapper.sqlserver.account;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.invite_code_mode.model.sqlserver.account.AccountsInfo;
import com.bamenyouxi.invite_code_mode.model.sqlserver.account.GroupRoom;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * mapper for userAgentInfor
 * Created by 13477 on 2017/8/31.
 */
@Mapper
public interface GroupRoomMapper extends CrudMapper<GroupRoom, Integer> {

    GroupRoom getStatus(Integer id);

    GroupRoom getRoom(Integer gameId);

    List<GroupRoom> queryCountRoom(int gameId);


}
