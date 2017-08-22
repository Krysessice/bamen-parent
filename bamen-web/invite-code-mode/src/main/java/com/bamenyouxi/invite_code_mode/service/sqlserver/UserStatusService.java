package com.bamenyouxi.invite_code_mode.service.sqlserver;

import com.bamenyouxi.core.impl.mapper.CrudMapper;
import com.bamenyouxi.core.impl.service.AbstractCrudService;
import com.bamenyouxi.invite_code_mode.mapper.sqlserver.treasure.UserStatusMapper;
import com.bamenyouxi.invite_code_mode.model.sqlserver.treasure.GameScoreLocker;
import com.bamenyouxi.invite_code_mode.model.sqlserver.treasure.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserStatusService extends AbstractCrudService<UserStatus,Integer> {

    @Autowired
    private UserStatusMapper userStatusMapper;
    @Override
    public CrudMapper<UserStatus, Integer> getMapper() {
        return userStatusMapper;
    }
    
}
