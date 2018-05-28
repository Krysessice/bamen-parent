package com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode;

import com.bamenyouxi.core.constant.AuthConstant;
import com.bamenyouxi.core.impl.model.mysql.BaseEntity;
import com.bamenyouxi.core.util.UUIDUtil;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * model for t_sys_agent
 * Created by hc on 2018/5/15.
 */
public class SysAgent extends BaseEntity {


    private String realName;
    private String tel;
    private String openingBank;
    private String bankAccount;
    private String province;
    private String city;
    private  Integer gameId;
    private String account;
    private String nickName;
    private String password;
    private String secretKey;
    private String superAgentId;
    private Integer  partner;
    private Date firstChargeTime;
    private Timestamp lastLoginTime;
    private BigDecimal payPrice;
    private Integer cardNum;
    private Integer cardGift;
    private Integer roleId;
    private Integer superRoleId;
    private String superName;
    private Boolean isFinishInfo;
    private BigDecimal withdraw;
    private BigDecimal clearPrice;
    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setOpeningBank(String openingBank) {
        this.openingBank = openingBank;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public void setPayPrice(BigDecimal payPrice) {
        this.payPrice = payPrice;
    }

    public BigDecimal getPayPrice() {
        return payPrice;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setFinishInfo(Boolean finishInfo) {
        isFinishInfo = finishInfo;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public void setSuperRoleId(Integer superRoleId) {
        this.superRoleId = superRoleId;
    }

    public void setSuperName(String superName) {
        this.superName = superName;
    }

    public void setSuperAccount(String superAccount) {
        this.superAccount = superAccount;
    }

    public BigDecimal getWithdraw() {
        return withdraw;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public Integer getSuperRoleId() {
        return superRoleId;
    }

    public String getSuperName() {
        return superName;
    }

    public Integer getCardGift() {
        return cardGift;
    }

    public Integer getCardNum() {
        return cardNum;
    }

    private String superAccount;    // 上级代理号
    private String superNickName;   // 上级代理昵称
    private Integer _superAgentAccount;
    private String _account;

    public BigDecimal getClearPrice() {
        return clearPrice;
    }

    public void setClearPrice(BigDecimal clearPrice) {
        this.clearPrice = clearPrice;
    }

    public String getRealName() {
        return realName;
    }

    public String getTel() {
        return tel;
    }

    public String getOpeningBank() {
        return openingBank;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public void setSuperAgentId(String superAgentId) {
        this.superAgentId = superAgentId;
    }

    public Integer getPartner() {
        return partner;
    }

    public SysAgent() {}

    public Integer get_superAgentAccount() {
        return _superAgentAccount;
    }

    public Boolean getFinishInfo() {
        return isFinishInfo;
    }

    public void emptySecretKey() {
        this.secretKey = null;
    }

    public String getAccount() {
        return account;
    }

    public String getNickName() {
        return nickName;
    }

    public String getPassword() {
        return password;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getSuperAgentId() {
        return superAgentId;
    }

    public Date getFirstChargeTime() {
        return firstChargeTime;
    }

    public Timestamp getLastLoginTime() {
        return lastLoginTime;
    }

    public String getSuperAccount() {
        return superAccount;
    }

    public String getSuperNickName() {
        return superNickName;
    }

    public SysAgent(Builder builder) {
        this.account = builder.account;
        this.nickName = builder.nickName;
        this.superAgentId = builder.superAgentId;
        this.sysFlag=builder.sysFlag;
        this.secretKey=builder.secretKey;
        this.password=builder.password;
        this.id=builder.id;
        this.partner=builder.partner;
        this.roleId=builder.roleId;
        this.superRoleId=builder.superRoleId;
        this.superName=builder.superName;
        this.gameId=builder.gameId;
        this.realName = builder.realName;
        this.tel = builder.tel;
        this.openingBank = builder.openingBank;
        this.bankAccount = builder.bankAccount;
        this.province = builder.province;
        this.city = builder.city;
        this.isFinishInfo=builder.isFinishInfo;
        this._superAgentAccount=builder._superAgentAccount;
        this._account=builder._account;
        this.withdraw=builder.withdraw;
    }


    public static class Builder {

        private Long id;
        private String account;
        private String nickName;
        private String superAgentId;
        private boolean sysFlag;
        private String secretKey;
        private  String password;
        private Integer  partner;
        private Integer roleId;
        private Integer superRoleId;
        private String superName;
        private Integer gameId;
        private Boolean isFinishInfo;
        private Boolean showAnnounce;
        private String realName;
        private String tel;
        private String openingBank;
        private String bankAccount;
        private String province;
        private String city;
        private Integer _superAgentAccount;
        private String _account;
        private BigDecimal withdraw;

        public Builder account(String val) {
            account = val;
            return this;
        }

        public Builder nickName(String val) {
            nickName = val;
            return this;
        }

        public Builder superAgentId(String val) {
            superAgentId = val;
            return this;
        }

        public Builder sysFlag(boolean val) {
            sysFlag = val;
            return this;
        }

        public Builder secretKey(String val) {
            secretKey = val;
            return this;
        }

        public Builder password(String val) {
            password = val;
            return this;
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder partner(Integer val) {
            partner = val;
            return this;
        }

        public Builder roleId(Integer val) {
            roleId = val;
            return this;
        }

        public Builder superRoleId(Integer val) {
            superRoleId = val;
            return this;
        }

        public Builder isFinishInfo(Boolean val) {
            isFinishInfo = val;
            return this;
        }

        public Builder superName(String  val) {
            superName = val;
            return this;
        }

        public Builder gameId(Integer val) {
            gameId = val;
            return this;
        }

        public Builder realName(String val) {
            realName = val;
            return this;
        }
        public Builder tel(String val) {
            tel = val;
            return this;
        }
        public Builder openingBank(String val) {
            openingBank = val;
            return this;
        }
        public Builder bankAccount(String val) {
            bankAccount = val;
            return this;
        }
        public Builder province(String val) {
            province = StringUtils.isEmpty(val) ? null : val;
            return this;
        }
        public Builder city(String val) {
            city = StringUtils.isEmpty(val) ? null : val;
            return this;
        }
        public Builder _superAgentAccount(Integer val) {
            _superAgentAccount = val;
            return this;
        }
        public Builder _account(String val) {
            _account = val;
            return this;
        }

        public Builder withdraw(BigDecimal val) {
            withdraw = val;
            return this;
        }
        public static void defaultPwdInject(SysAgent sysAgent) {
            String secretKey = UUIDUtil.genUUID();
            String password = new Md5PasswordEncoder().encodePassword(AuthConstant.SysAgentConstant.DEFAULT_PWD, secretKey);
            sysAgent.secretKey = secretKey;
            sysAgent.password = password;
        }


        public SysAgent build() {
            return new SysAgent(this);
        }


    }
}
