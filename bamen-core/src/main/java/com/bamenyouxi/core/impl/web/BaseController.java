package com.bamenyouxi.core.impl.web;

import com.bamenyouxi.core.impl.service.BaseService;

import java.io.Serializable;

/**
 * top controller interface
 * Created by 13477 on 2017/7/10.
 */
public interface BaseController<T, ID extends Serializable> {

	BaseService<T, ID> getService();
}
