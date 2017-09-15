package com.bamenyouxi.room_card_mode.web;

import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import com.bamenyouxi.core.model.result.WebResult;
import com.bamenyouxi.room_card_mode.util.UserDetailsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 全局异常处理器
 * Created by 13477 on 2017/6/26.
 */
@ControllerAdvice
final class GlobalExceptionHandler {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 返回异常信息
	 * @param e         异常信息
	 * @param response  响应对象
	 * @throws IOException  io异常
	 * @return WebResult
	 */
	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	private WebResult exceptionHandler(RuntimeException e, HttpServletResponse response) throws IOException {
		if (TipMsgConstant.OPERATION_EXCEPTION_AND_LOGOUT.equals(e.getLocalizedMessage())) {
			stringRedisTemplate.delete(UserDetailsUtil.getRedisKey_Salt());
			SecurityContextHolder.clearContext();
		}
		return WebResult.of(SysConstant.SysFlagConstant.DISABLE, e.getLocalizedMessage(), null);
	}
}
