package com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode;

import com.bamenyouxi.core.impl.model.mysql.BaseEntity;

public class Role extends BaseEntity {

    private int  roleId;
    private String roleName;
    private int  parentId;

    public int getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public int getParentId() {
        return parentId;
    }

}
