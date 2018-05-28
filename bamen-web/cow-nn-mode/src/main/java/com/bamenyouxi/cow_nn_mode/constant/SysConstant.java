package com.bamenyouxi.cow_nn_mode.constant;

/**
 * 系统常量
 * Created by 13477 on 2017/8/23.
 */
public final class SysConstant {

	public enum SysAgentConstant {
		ACCOUNT_LENGTH("11");

		private String val;

		SysAgentConstant(String val) {
			this.val = val;
		}

		public String getVal() {
			return val;
		}
	}

	public static int getAccountLength() {
		return Integer.valueOf(SysConstant.SysAgentConstant.ACCOUNT_LENGTH.getVal());
	}
}
