package com.bamenyouxi.core.model.result;

import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.model.result.impl.AbstractResult;

/**
 * web api 响应结果集
 * Created by 13477 on 2017/6/23.
 */
public final class WebResult<T> extends AbstractResult<T> {

	public static WebResult of() {
		return of(null);
	}

	public static <T> WebResult of(T content) {
		return of(SysConstant.SysFlagConstant.ENABLE, null, content);
	}

	public static <T> WebResult of(int status, String msg, T content) {
		try {
			return new WebResult<>(status, msg, content);
		} catch (Exception e) {
			return new WebResult<>(SysConstant.SysFlagConstant.DISABLE, e.getLocalizedMessage(), null);
		}
	}

	private WebResult(int status, String msg, T content) {
		this.status = status;
		this.msg = msg;
		this.content = content;
	}

	@Override
	public int getStatus() {
		return status;
	}

	@Override
	public String getStatusCode() {
		return statusCode;
	}

	@Override
	public String getMsg() {
		return msg;
	}

	@Override
	public T getContent() {
		return content;
	}
}
