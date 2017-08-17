package com.bamenyouxi.core.impl.service;

import com.bamenyouxi.core.impl.mapper.BaseMapper;

import java.io.Serializable;

/**
 * top service interface
 * Created by 13477 on 2017/6/27.
 */
public interface BaseService<T, ID extends Serializable> {

	BaseMapper<T, ID> getMapper();
}
