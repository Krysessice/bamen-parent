package com.bamenyouxi.core.util;

import java.util.UUID;
import java.util.stream.Stream;

/**
 * uuid工具类
 * Created by 13477 on 2017/6/28.
 */
public final class UUIDUtil {

	/**
	 * 获取生成uuid的流
	 * @return Stream
	 */
	private static Stream<String> getUUIDStream() {
		return Stream.generate(UUID::randomUUID).map(UUID::toString);
	}

	/**
	 * 获取一个uuid
	 * @return String
	 */
	public static String genUUID() {
		return getUUIDStream().limit(1).findFirst().orElse(null);
	}
}
