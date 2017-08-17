package com.bamenyouxi.core.model.result.impl;

/**
 * 结果集抽象类
 * Created by 13477 on 2017/6/23.
 */
public abstract class AbstractResult<T> implements Result<T> {
	protected int status;
	protected String statusCode;
	protected String msg;
	protected T content;
}
