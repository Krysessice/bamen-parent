package com.bamenyouxi.invite_code_mode.constant;

/**
 * 系统常量
 * Created by 13477 on 2017/9/4.
 */
public final class SysConstant extends com.bamenyouxi.core.constant.SysConstant {

	public enum EnumValue1 {

	}

	public enum EnumValue2 {
		GROUP_NAME_LENGTH(2, 10);

		private Object value1;
		private Object value2;

		EnumValue2(Object value1, Object value2) {
			this.value1 = value1;
			this.value2 = value2;
		}

		public Object getValue1() {
			return value1;
		}

		public Object getValue2() {
			return value2;
		}
	}
}
