package com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode;

import com.bamenyouxi.core.impl.model.mysql.BaseEntity;

public class Price extends BaseEntity {

    private Integer rmb;
    private Integer drill;
    private String image;


    public String getImage() {
        return image;
    }
    public Integer getRmb() {
        return rmb;
    }
    public Integer getDrill() {
        return drill;
    }

}
