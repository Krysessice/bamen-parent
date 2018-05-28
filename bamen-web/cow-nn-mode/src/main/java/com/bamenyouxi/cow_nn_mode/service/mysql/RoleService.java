package com.bamenyouxi.cow_nn_mode.service.mysql;

import com.bamenyouxi.core.constant.FieldConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.cow_nn_mode.mapper.mysql.cow_nn_mode.RoleMapper;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.Role;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleService extends AbstractCrudService<Role, Long>{

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public CrudMapper<Role, Long> getMapper() {
        return roleMapper;
    }

    @Transactional
    public PageInfo<Role> getOfficeLimits(){
        List<Role> list=roleMapper.getOfficeLimits();
        if (list.isEmpty() || list.get(0) == null)
            return new PageInfo<>();
        return new PageInfo<>(list);
    }

    @Transactional
    public PageInfo<Role> getSalesmanLimits(){
        List<Role> list=roleMapper.getSalesmanLimits();
        if (list.isEmpty() || list.get(0) == null)
            return new PageInfo<>();
        return new PageInfo<>(list);
    }

    @Transactional
    public PageInfo<Role> getAgent(){
        List<Role> list=roleMapper.getAgent();
        if (list.isEmpty() || list.get(0) == null)
            return new PageInfo<>();
        return new PageInfo<>(list);
    }

    @Transactional
    public PageInfo<Role> getZdOneAgent(){
        List<Role> list=roleMapper.getZdOneAgent();
        if (list.isEmpty() || list.get(0) == null)
            return new PageInfo<>();
        return new PageInfo<>(list);
    }


}
