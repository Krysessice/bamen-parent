package com.bamenyouxi.core.util;

import com.bamenyouxi.core.constant.TipMsgConstant;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * 通用工具类
 * Created by 13477 on 2017/6/23.
 */
public final class CommonUtil {

	/**
	 * 判断字符串是否为整数
	 * @param s 待校验字符串
	 * @return boolean
	 */
	public static boolean isNumeric(String s) {
		return Pattern.compile("^[-+]?[\\d]*$").matcher(s).matches();
	}

	/**
	 * 判断list集合是否为空
	 * <p>空返回包含一个null值的集合，非空返回本身</p>
	 * @param list  集合
	 * @param <T>   泛型
	 * @return  List
	 */
	public static <T> List<T> isEmpty(List<T> list) {
		return list.isEmpty() ? new ArrayList<T>() {{
			add(null);
		}} : list;
	}

	/**
	 * 创建map对象 (Deprecated)
	 * @param keys      键集
	 * @param values    值集
	 * @param <k>       键对象类型
	 * @param <v>       值对象类型
	 * @return  map集合
	 */
	@Deprecated
	public static <k, v> Map<k, v> initMap(k[] keys, v[] values) {
		if (keys == null || values == null)
			return new HashMap<>();
		Assert.isTrue(keys.length == values.length, TipMsgConstant.PARAM_INVALID);
		return new HashMap<k, v>() {{
			IntStream.range(0, keys.length)
					.forEach(i -> put(keys[i], values[i]));
		}};
	}

	/* Deprecated */
	@Deprecated
	@SuppressWarnings({"unchecked", "deprecation"})
	public static String getYmlValue(Map<String, Object> ymlMap, String path) {
		String[] keys = path.split("\\.", 2);
		Assert.isTrue(ymlMap.containsKey(keys[0]), String.format("key[%s] is not exist", keys[0]));

		Object obj = ymlMap.get(keys[0]);
		boolean bn = (obj instanceof Map);
		Assert.isTrue(!(keys.length == 1 && bn), String.format("value of key[%s] is a map, perhaps u may find deeper?", keys[0]));

		if (bn)
			return getYmlValue((Map<String, Object>) obj, keys[1]);
		else
			return String.valueOf(obj);
	}
}
