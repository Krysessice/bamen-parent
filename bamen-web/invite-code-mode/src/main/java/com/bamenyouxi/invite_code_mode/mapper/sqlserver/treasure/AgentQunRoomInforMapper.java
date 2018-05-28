package com.bamenyouxi.invite_code_mode.mapper.sqlserver.treasure;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.invite_code_mode.model.sqlserver.treasure.AgentQunRoomInfor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AgentQunRoomInforMapper extends CrudMapper<AgentQunRoomInfor, Integer> {


    List<AgentQunRoomInfor> getID(Integer QunID);
}
