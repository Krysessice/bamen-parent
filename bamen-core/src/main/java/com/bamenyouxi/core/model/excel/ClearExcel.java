package com.bamenyouxi.core.model.excel;

import com.bamenyouxi.core.model.excel.impl.AbstractExportExcel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 结算报表格式
 * Created by 13477 on 2017/7/29.
 */
public class ClearExcel<T extends ClearExcel.Field> extends AbstractExportExcel<ClearExcel.Field> {

	private ClearExcel(Builder builder) {
		this.parent = builder.parent;
		this.child = builder.child;
		this.sheetName = builder.sheetName;
		this.titles = builder.titles;
		this.fields = builder.fields;
	}

	public static class Builder {
		private String parent;
		private String child;
		private String sheetName;
		private List<String> titles;
		private List<Field> fields;

		public Builder parent(String val) {
			this.parent = val;
			return this;
		}
		public Builder child(String val) {
			this.child = val;
			return this;
		}
		public Builder sheetName(String val) {
			this.sheetName = val;
			return this;
		}
		public Builder titles(List<String> val) {
			this.titles = val;
			return this;
		}
		public Builder fields(Field field) {
			if (fields == null)
				fields = new ArrayList<>();
			fields.add(field);
			return this;
		}

		public static class BuilderField {
			private static int serialNumber = 0;
			private String bankAccount;
			private String accountName;
			private BigDecimal clearPrice;
			private String openingBank;
			private String province;
			private String city;
			private Integer gameId;
			private String nickName;

			public static void reset() {
				serialNumber = 0;
			}

			public BuilderField serialNumber() {
				++serialNumber;
				return this;
			}
			public BuilderField serialNumber(int val) {
				serialNumber = val;
				return this;
			}
			public BuilderField accountName(String val) {
				accountName = val;
				return this;
			}
			public BuilderField bankAccount(String val) {
				bankAccount = val;
				return this;
			}
			public BuilderField clearPrice(BigDecimal val) {
				clearPrice = val;
				return this;
			}
			public BuilderField openingBank(String val) {
				openingBank = val;
				return this;
			}
			public BuilderField province(String val) {
				province = val;
				return this;
			}
			public BuilderField city(String val) {
				city = val;
				return this;
			}
			public BuilderField gameId(Integer val) {
				gameId = val;
				return this;
			}
			public BuilderField nickName(String val) {
				nickName = val;
				return this;
			}

			public Field build() {
				return new Field(this);
			}
		}

		public ClearExcel<ClearExcel.Field> build() {
			return new ClearExcel<>(this);
		}
	}

	static class Field implements AbstractExportExcel.Field {
		private String accountType = "个人账户";
		private String bankAccount;
		private String accountName;
		private BigDecimal clearPrice;
		private String payReason = "业务费";
		private String openingBank;
		private String subbranch;       //支行
		private String province;
		private String city;
		private String transOutAccount; //转出账号
		private String payType = "实时";
		private String transferState;   //转账状态
		private Integer gameId;
		private String nickName;

		Field(Builder.BuilderField build) {
			this.bankAccount = build.bankAccount;
			this.accountName = build.accountName;
			this.clearPrice = build.clearPrice;
			this.openingBank = build.openingBank;
			this.province = build.province;
			this.city = build.city;
			this.gameId = build.gameId;
			this.nickName = build.nickName;
		}
	}
}
