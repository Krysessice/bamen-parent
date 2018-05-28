package com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode;

import com.bamenyouxi.core.impl.model.mysql.BaseEntity;

public class Scale extends BaseEntity {

    private  String rebaterateName;
    private  Integer rebaterate;

    public String getRebaterateName() {
        return rebaterateName;
    }

    public Integer getRebaterate() {
        return rebaterate;
    }
}
