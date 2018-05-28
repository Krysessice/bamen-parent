package com.bamen;

import javax.servlet.http.HttpServletRequest;

public class IpUtil {

	/** 获取真实IP * */
	public static String getRealIp(HttpServletRequest request) {
		/*
		 * 目前网上流行的所谓“取真实IP地址”的方法，都有bug，没有考虑到多层透明代理的情况。 多数代码类似： string IpAddress =
		 * (HttpContext.Current.Request.ServerVariables["HTTP_X_FORWARDED_FOR"]!=null &&
		 * HttpContext.Current.Request.ServerVariables["HTTP_X_FORWARDED_FOR"]
		 * !=String.Empty)
		 * ?HttpContext.Current.Request.ServerVariables["HTTP_X_FORWARDED_FOR"]
		 * :HttpContext.Current.Request.ServerVariables["REMOTE_ADDR"];
		 * 事实上，上面的代码只试用与用户只使用了1层代理，如果用户有2层，3层HTTP_X_FORWARDED_FOR
		 * 的值是：“本机真实IP,1层代理IP,2层代理IP,.....” ，
		 * 如果这个时候你的数据中保存IP字段的长度很小（15个字节），数据库就报错了。 所以取“真正”IP地址的方式，还应该判断
		 * “HTTP_X_FORWARDED_FOR” 中是否有“,”逗号，或者长度是否超长（超过15字节 xxx.xxx.xxx.xxx）。
		 */
		String result = getXForwardIp(request);
		if (result != null && result.trim().length() > 0) {
			// 可能有代理
			if (result.indexOf(".") == -1) {
				// 没有“.”肯定是非IPv4格式
				result = null;
			} else {
				if (result.indexOf(",") != -1) {
					// 有“,”，估计多个代理。取第一个不是内网的IP。
					result = result.trim().replace("'", "");
					String[] temparyip = result.split(",");
					for (int i = 0; i < temparyip.length; i++) {
						String tempIp = temparyip[i].trim();
						if (isIPAddress(tempIp) && !isLanAddress(tempIp)) {
							result = tempIp; // 找到不是内网的地址
							break;
						}
					}
				} else if (isIPAddress(result) && !isLanAddress(result)) {// 代理即是IP格式
					return result;
				} else {
					result = null; // 代理中的内容 非IP，取IP
				}
			}
		}

		if (result == null || result.trim().length() == 0) {
			result = request.getHeader("Proxy-Client-IP");
		}
		if (result == null || result.trim().length() == 0) {
			result = request.getRemoteAddr();
			if("0:0:0:0:0:0:0:1".equals(result)){//hosts 文件搞的鬼：	::1 localhost
				result = "127.0.0.1";
			}
		}
		return result;
	}

	/** 判断是否是局域网IP地址格式 * */
	public static boolean isLanAddress(String str) {
		return str.startsWith("10.")
				|| str.startsWith("192.168.")
				|| str.startsWith("172.16.")
				|| str.startsWith("172.17.")
				|| str.startsWith("172.18.")
				|| str.startsWith("172.19.")
				|| str.startsWith("172.20.")
				|| str.startsWith("172.21.")
				|| str.startsWith("172.22.")
				|| str.startsWith("172.23.")
				|| str.startsWith("172.24.")
				|| str.startsWith("172.25.")
				|| str.startsWith("172.26.")
				|| str.startsWith("172.27.")
				|| str.startsWith("172.28.")
				|| str.startsWith("172.29.")
				|| str.startsWith("172.30.")
				|| str.startsWith("172.31.");
	}
	
	/** 判断是否是IP地址格式 * */
	public static boolean isIPAddress(String str1) {
		if (str1 == null || str1.trim().length() < 7
				|| str1.trim().length() > 15) {
			return false;
		}
		return true;
	}

	public static String getXForwardIp(HttpServletRequest request) {
//		return request.getHeader("x-forwarded-for");
		return request.getHeader("X-Forwarded-For");// http_x_forward
	}
	
	public static String getRemoteAddr(HttpServletRequest request) {
//		return request.getHeader("X-Real-IP");// remote_addr
		return request.getRemoteAddr();//remote_addr
	} 
	
//	/**
//	 * 获取真实IP地址
//	 * */
//	public static String getRealIp(HttpServletRequest request) {
//		String IP = "";
//		String xforward = IpUtil.getXForwardIp(request);// http_x_forward
//		String remoteAddr = IpUtil.getRemoteAddr(request);// remote_addr
//		if (CommonUtils.isNotEmpty(xforward)) {
//			String[] str = xforward.split(",");
//			if (str.length > 1) {
//				String sip = str[str.length - 1].trim();
//				if (CommonUtils.isNotEmpty(sip) && CommonUtils.isNotEmpty(remoteAddr)) {
//					if (sip.equals(remoteAddr.trim())) {
//						IP = sip;
//					} else {
//						IP = str[0].trim();
//					}
//				} else {
//					IP = str[0].trim();
//				}
//			} else {
//				IP = remoteAddr;
//			}
//		} else {
//			IP = remoteAddr;
//		}
//		return IP;
//	}
	
	
	
	
	
	
	public static void main(String[] args) {
		String result = "10.73.22.230, 211.136.222.106";
		if (result != null && result.trim().length() > 0) {
			// 可能有代理
			if (result.indexOf(".") == -1) {
				// 没有“.”肯定是非IPv4格式
				result = null;
			} else {
				if (result.indexOf(",") != -1) {
					// 有“,”，估计多个代理。取第一个不是内网的IP。
					result = result.trim().replace("'", "");
					String[] temparyip = result.split(",");
					for (int i = 0; i < temparyip.length; i++) {
						String tempIp = temparyip[i].trim();
						if (isIPAddress(tempIp)
								&& !tempIp.startsWith("10.")
								&& !tempIp.startsWith("192.168")
								&& !tempIp.startsWith("172.16.")) {
							result = tempIp; // 找到不是内网的地址
						}
					}
				} else if (isIPAddress(result)) {// 代理即是IP格式
				} else {
					result = null; // 代理中的内容 非IP，取IP
				}
			}
		}

		System.out.println(result);
	}
}
