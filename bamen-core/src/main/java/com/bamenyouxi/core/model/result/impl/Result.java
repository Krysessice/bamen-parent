package com.bamenyouxi.core.model.result.impl;

/**
 * 响应结果集顶层接口
 * Created by 13477 on 2017/6/23.
 */
interface Result<T> {

	int getStatus();

	String getStatusCode(); //错误码

	String getMsg();

	T getContent();
}
