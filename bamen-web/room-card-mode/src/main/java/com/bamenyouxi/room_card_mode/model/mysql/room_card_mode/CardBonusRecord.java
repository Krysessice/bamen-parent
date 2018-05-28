package com.bamenyouxi.room_card_mode.model.mysql.room_card_mode;

import com.bamenyouxi.core.constant.AuthConstant;
import com.bamenyouxi.core.impl.model.mysql.BaseEntity;
import com.bamenyouxi.core.util.UUIDUtil;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

/**
 * model for t_card_bonus_record
 * Created by 13477 on 2017/8/23.
 */
public final class CardBonusRecord extends BaseEntity {

	private String account;
	private String nickName;
	private String sysAgentId;
	private Long payOrderId;
	private String firSuperAgentId;   //上级代理主键id
	private Integer firBonus;
	private String secSuperAgentId;   //上上级代理主键id
	private Integer secBonus;

	public String getAccount() {
		return account;
	}

	public String getNickName() {
		return nickName;
	}

	public CardBonusRecord() {}

	public String getSysAgentId() {
		return sysAgentId;
	}

	public Long getPayOrderId() {
		return payOrderId;
	}

	public void setFirSuperAgentId(String firSuperAgentId) {
		this.firSuperAgentId = firSuperAgentId;
	}

	public void setSecSuperAgentId(String secSuperAgentId) {
		this.secSuperAgentId = secSuperAgentId;
	}

	public Integer getFirBonus() {
		return firBonus;
	}

	public String getFirSuperAgentId() {
		return firSuperAgentId;
	}

	public String getSecSuperAgentId() {
		return secSuperAgentId;
	}

	public Integer getSecBonus() {
		return secBonus;
	}

	public void setSysAgentId(String sysAgentId) {
		this.sysAgentId = sysAgentId;
	}

	public void setPayOrderId(Long payOrderId) {
		this.payOrderId = payOrderId;
	}


	public void setFirBonus(Integer firBonus) {
		this.firBonus = firBonus;
	}



	public void setSecBonus(Integer secBonus) {
		this.secBonus = secBonus;
	}


	private CardBonusRecord(CardBonusRecord.Builder builder) {
		this.sysAgentId = builder.sysAgentId;
		this.payOrderId = builder.payOrderId;
		this.firSuperAgentId = builder.firSuperAgentId;
		this.firBonus=builder.firBonus;
		this.secSuperAgentId=builder.secSuperAgentId;
		this.secBonus=builder.secBonus;
	}

	public static class Builder {

		private String sysAgentId;
		private Long payOrderId;
		private String firSuperAgentId;   //上级代理主键id
		private Integer firBonus;
		private String secSuperAgentId;   //上上级代理主键id
		private Integer secBonus;

		public CardBonusRecord.Builder sysAgentId(String val) {
			sysAgentId = val;
			return this;
		}

		public CardBonusRecord.Builder payOrderId(Long val) {
			payOrderId = val;
			return this;
		}

		public CardBonusRecord.Builder firSuperAgentId(String val) {
			firSuperAgentId = val;
			return this;
		}

		public CardBonusRecord.Builder firBonus(Integer val) {
			firBonus = val;
			return this;
		}

		public CardBonusRecord.Builder secSuperAgentId(String val) {
			secSuperAgentId = val;
			return this;
		}

		public CardBonusRecord.Builder secBonus(Integer val) {
			secBonus = val;
			return this;
		}


		public CardBonusRecord build() {
			return new CardBonusRecord(this);
		}

	}
}
