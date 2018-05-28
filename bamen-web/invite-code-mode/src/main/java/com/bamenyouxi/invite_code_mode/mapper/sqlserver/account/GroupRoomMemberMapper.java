package com.bamenyouxi.invite_code_mode.mapper.sqlserver.account;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.invite_code_mode.model.sqlserver.account.GroupRoom;
import com.bamenyouxi.invite_code_mode.model.sqlserver.account.GroupRoomMember;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * mapper for userAgentmap
 * Created by 13477 on 2017/8/31.
 */
@Mapper
public interface GroupRoomMemberMapper extends CrudMapper<GroupRoomMember, Long> {


    List<GroupRoomMember> getGroupRomm(Integer gameId);

    int deleteRoom(Integer groupRoomId);

    int deletePlays(Integer UserId);
}
