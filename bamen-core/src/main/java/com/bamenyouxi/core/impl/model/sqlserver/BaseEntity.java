package com.bamenyouxi.core.impl.model.sqlserver;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * sqlserver model top class
 * Created by 13477 on 2017/7/10.
 */
public abstract class BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
