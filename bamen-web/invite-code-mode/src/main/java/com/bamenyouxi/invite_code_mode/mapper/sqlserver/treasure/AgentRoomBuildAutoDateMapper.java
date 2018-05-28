package com.bamenyouxi.invite_code_mode.mapper.sqlserver.treasure;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.invite_code_mode.model.sqlserver.treasure.AgentQunRoomInfor;
import com.bamenyouxi.invite_code_mode.model.sqlserver.treasure.AgentRoomBuildAutoDate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AgentRoomBuildAutoDateMapper extends CrudMapper<AgentRoomBuildAutoDate, Integer> {

    int setUp();
}
