package com.bamenyouxi.room_card_mode.model.mysql.room_card_mode;

import com.bamenyouxi.core.constant.AuthConstant;
import com.bamenyouxi.core.impl.model.mysql.BaseEntity;
import com.bamenyouxi.core.util.UUIDUtil;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import java.math.BigDecimal;

/**
 * model for t_pay_order
 * Created by 13477 on 2017/8/23.
 */
public final class PayOrder extends BaseEntity {

	private String account;
	private String nickName;
	private String orderNo;
	private Long sysAgentId;
	private BigDecimal payPrice;
	private Integer cardNum;
	private Integer cardGift;

	private PayOrder() {}

	public String getAccount() {
		return account;
	}

	public String getNickName() {
		return nickName;
	}

	public void setSysAgentId(Long sysAgentId) {
		this.sysAgentId = sysAgentId;
	}

	public Integer getCardGift() {
		return cardGift;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public Long getSysAgentId() {
		return sysAgentId;
	}

	public BigDecimal getPayPrice() {
		return payPrice;
	}

	public Integer getCardNum() {
		return cardNum;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	private PayOrder(PayOrder.Builder builder) {
		this.orderNo=builder.orderNo;
		this.sysAgentId=builder.sysAgentId;
		this.payPrice=builder.payPrice;
		this.cardNum=builder.cardNum;
		this.cardGift=builder.cardGift;
	}

	public static class Builder {

		private String orderNo;
		private Long sysAgentId;
		private BigDecimal payPrice;
		private Integer cardNum;
		private Integer cardGift;

		public PayOrder.Builder orderNo(String val) {
			orderNo = val;
			return this;
		}

		public PayOrder.Builder sysAgentId(Long val) {
			sysAgentId = val;
			return this;
		}

		public PayOrder.Builder payPrice(BigDecimal val) {
			payPrice = val;
			return this;
		}

		public PayOrder.Builder cardNum(Integer val) {
			cardNum = val;
			return this;
		}

		public PayOrder.Builder cardGift(Integer val) {
			cardGift = val;
			return this;
		}


		public PayOrder build() {
			return new PayOrder(this);
		}

	}
}
