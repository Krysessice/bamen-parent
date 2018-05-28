package com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode;

import com.bamenyouxi.core.impl.model.mysql.BaseEntity;

/**
 * model for t_pay_gold_record
 * Created by 13477 on 2018/4/28.
 */
public final class PayGoldRecord extends BaseEntity {

	private String goldPrice;
	private Integer  goldNum;
	private String selected;
	private Integer presenter;
	private Integer presentee;
	private String payReason;

	public String getGoldPrice() {
		return goldPrice;
	}

	public Integer getGoldNum() {
		return goldNum;
	}

	public String getSelected() {
		return selected;
	}

	public Integer getPresenter() {
		return presenter;
	}

	public Integer getPresentee() {
		return presentee;
	}

	public String getPayReason() {
		return payReason;
	}

	public static PayGoldRecord of(Integer presenter, Integer presentee,String goldPrice, Integer goldNum,String selected) {
		return of(presenter, presentee,goldPrice, goldNum,selected, null);
	}

	public static PayGoldRecord of(Integer presenter, Integer presentee, String goldPrice, Integer goldNum, String selected, String payReason) {
		return new PayGoldRecord(presenter, presentee, goldPrice,goldNum,selected, payReason);
	}

	public PayGoldRecord() {}

	private PayGoldRecord(Integer presenter, Integer presentee, String goldPrice, Integer goldNum, String selected,String payReason) {
		this.presenter = presenter;
		this.presentee = presentee;
		this.goldNum = goldNum;
		this.goldPrice=goldPrice;
		this.selected=selected;
		this.payReason = payReason;
	}
}
