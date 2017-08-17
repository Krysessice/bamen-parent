package com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode;

import com.bamenyouxi.core.constant.AuthConstant;
import com.bamenyouxi.core.constant.SysConstant;
import com.bamenyouxi.core.util.UUIDUtil;
import com.bamenyouxi.invite_code_mode.model.mysql.invite_code_mode.impl.AbstractUserInfoEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

/**
 * model for t_sys_agent
 * Created by hc on 2017/7/9.
 */
public final class SysAgent extends AbstractUserInfoEntity {

    private String realName;
    private String tel;
    private String openingBank;
    private String bankAccount;
    private Boolean isFinishInfo;
    private String password;
    private String secretKey;
    private Timestamp lastLoginTime;
    private Integer superAgentGameId;
    @JsonFormat(locale = SysConstant.JsonFormat.DEFAULT_LOCAL, timezone = SysConstant.JsonFormat.DEFAULT_TIMEZONE, pattern = SysConstant.JsonFormat.DEFAULT_PATTERN)
    private Timestamp transMemberTime;
    private Boolean isAuthorized;
    @JsonFormat(locale = SysConstant.JsonFormat.DEFAULT_LOCAL, timezone = SysConstant.JsonFormat.DEFAULT_TIMEZONE, pattern = SysConstant.JsonFormat.DEFAULT_PATTERN)
    private Timestamp authorizedTime;
    private Integer recruitNum;
    private Boolean showAnnounce;

    private BigDecimal clearPrice;
    private Integer _gameId;
    private Integer _superAgentGameId;

    private int recruitNumInt = 0; //招募人数的操作，1自增，-1自减，0无操作，默认0

    private SysAgent() {}

    public void emptySecretKey() {
        this.secretKey = null;
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

    public Boolean getFinishInfo() {
        return isFinishInfo;
    }

    public String getPassword() {
        return password;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public Timestamp getLastLoginTime() {
        return lastLoginTime;
    }

    public Integer getSuperAgentGameId() {
        return superAgentGameId;
    }

    public Timestamp getTransMemberTime() {
        return transMemberTime;
    }

    public Boolean getAuthorized() {
        return isAuthorized;
    }

    public Timestamp getAuthorizedTime() {
        return authorizedTime;
    }

    public Integer getRecruitNum() {
        return recruitNum;
    }

    public Boolean getShowAnnounce() {
        return showAnnounce;
    }

    public BigDecimal getClearPrice() {
        return clearPrice;
    }

    public Integer get_gameId() {
        return _gameId;
    }

    public Integer get_superAgentGameId() {
        return _superAgentGameId;
    }

    public int getRecruitNumInt() {
        return recruitNumInt;
    }

    public void setClearPrice(BigDecimal clearPrice) {
        this.clearPrice = clearPrice;
    }

    private SysAgent(Builder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.gameId = builder.gameId;
        this.accounts = builder.accounts;
        this.nickName = builder.nickName;
        this.realName = builder.realName;
        this.tel = builder.tel;
        this.openingBank = builder.openingBank;
        this.bankAccount = builder.bankAccount;
        this.isFinishInfo = builder.isFinishInfo;
        this.password = builder.password;
        this.secretKey = builder.secretKey;
        this.superAgentGameId = builder.superAgentGameId;
        this.transMemberTime = builder.transMemberTime;
        this.isAuthorized = builder.isAuthorized;
        this.authorizedTime = builder.authorizedTime;
        this.recruitNum = builder.recruitNum;
        this.showAnnounce = builder.showAnnounce;
        this.sysFlag = builder.sysFlag;
        this.createTime = builder.createTime;

        this._gameId = builder._gameId;
        this._superAgentGameId = builder._superAgentGameId;

        this.recruitNumInt = builder.recruitNumInt;
    }

    public static class Builder {
        private Long id;
        private Integer userId;
        private Integer gameId;
        private String accounts;
        private String nickName;
        private String realName;
        private String tel;
        private String openingBank;
        private String bankAccount;
        private Boolean isFinishInfo;
        private String password;
        private String secretKey;
        private Integer superAgentGameId;
        private Timestamp transMemberTime;
        private Boolean isAuthorized;
        private Timestamp authorizedTime;
        private Integer recruitNum;
        private Boolean showAnnounce;
        private Boolean sysFlag;
        private Timestamp createTime;

        private Integer _gameId;
        private Integer _superAgentGameId;

        private int recruitNumInt;

        public Builder id(Long val) {
            id = val;
            return this;
        }
        public Builder userId(Integer val) {
            userId = val;
            return this;
        }
        public Builder gameId(Integer val) {
            gameId = val;
            return this;
        }
        public Builder accounts(String val) {
            accounts = val;
            return this;
        }
        public Builder nickName(String val) {
            nickName = val;
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
        public Builder isFinishInfo(Boolean val) {
            isFinishInfo = val;
            return this;
        }
        public Builder password(String val) {
            password = val;
            return this;
        }
        public Builder secretKey(String val) {
            secretKey = val;
            return this;
        }
        public Builder superAgentGameId(Integer val) {
            superAgentGameId = val;
            return this;
        }
        public Builder transMemberTime(Timestamp val) {
            transMemberTime = val;
            return this;
        }
        public Builder isAuthorized(Boolean val) {
            isAuthorized = val;
            return this;
        }
        public Builder authorizedTime(Timestamp val) {
            authorizedTime = val;
            return this;
        }
        public Builder recruitNum(Integer val) {
            recruitNum = val;
            return this;
        }
        public Builder showAnnounce(Boolean val) {
            showAnnounce = val;
            return this;
        }
        public Builder sysFlag(Boolean val) {
            sysFlag = val;
            return this;
        }
        public Builder createTime(Timestamp val) {
            createTime = val;
            return this;
        }

        public Builder _superAgentGameId(Integer val) {
            _superAgentGameId = val;
            return this;
        }
        public Builder _gameId(Integer val) {
            _gameId = val;
            return this;
        }

        public Builder recruitNumInt(int val) {
            recruitNumInt = val;
            return this;
        }

        public static void defaultPwdInject(SysAgent sysAgent) {
            String secretKey = UUIDUtil.genUUID();
            String password = new Md5PasswordEncoder().encodePassword(AuthConstant.SysAgentConstant.DEFAULT_PWD, secretKey);
            sysAgent.secretKey = secretKey;
            sysAgent.password = password;
        }

        /**
         * 利用反射改变对象属性
         * @param sysAgent  代理对象
         * @param params    属性集
         * @return  SysAgent
         */
        public static SysAgent plus(SysAgent sysAgent, Map<String, Object> params) {
            params.forEach((k, v) -> {
                Field field;
                try {
                    field = SysAgent.class.getDeclaredField(k);
                    field.set(sysAgent, v);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
            return sysAgent;
        }

        public SysAgent build() {
            return new SysAgent(this);
        }
    }
}
