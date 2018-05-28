package com.bamenyouxi.cow_nn_mode.mapper.mysql.cow_nn_mode;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper  extends CrudMapper<Role, Long> {

    List<Role> getOfficeLimits();
    List<Role> getSalesmanLimits();
    List<Role> getAgent();
    List<Role> getZdOneAgent();
}
