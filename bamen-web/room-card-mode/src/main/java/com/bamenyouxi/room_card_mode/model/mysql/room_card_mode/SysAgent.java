package com.bamenyouxi.room_card_mode.model.mysql.room_card_mode;

import com.bamenyouxi.core.constant.AuthConstant;
import com.bamenyouxi.core.impl.model.mysql.BaseEntity;
import com.bamenyouxi.core.util.UUIDUtil;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import java.sql.Timestamp;
import java.util.Date;

/**
 * model for t_sys_agent
 * Created by hc on 2017/7/9.
 */
public final class SysAgent extends BaseEntity {

    private String account;
    private String nickName;
    private String password;
    private String secretKey;
    private Long superAgentId;
    private Date firstChargeTime;
    private Timestamp lastLoginTime;


    private String superAccount;    // 上级代理号
    private String superNickName;   // 上级代理昵称


    private SysAgent() {}

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

    public Long getSuperAgentId() {
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

    private SysAgent(Builder builder) {
        this.account = builder.account;
        this.nickName = builder.nickName;
        this.superAgentId = builder.superAgentId;
        this.sysFlag=builder.sysFlag;
        this.secretKey=builder.secretKey;
        this.password=builder.password;
        this.id=builder.id;
    }

    public static class Builder {

        private Long id;
        private String account;
        private String nickName;
        private Long superAgentId;
        private boolean sysFlag;
        private String secretKey;
        private  String password;

        public Builder account(String val) {
            account = val;
            return this;
        }

        public Builder nickName(String val) {
            nickName = val;
            return this;
        }

        public Builder superAgentId(Long val) {
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
