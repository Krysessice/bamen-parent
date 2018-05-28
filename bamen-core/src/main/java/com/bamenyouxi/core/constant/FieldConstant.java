package com.bamenyouxi.core.constant;

/**
 * 字段常量
 * Created by hc on 2017/7/9.
 */
public final class FieldConstant {

    /**
     * 数据库字段常量
     */
    public enum DBFieldConstant {
        F_ID, F_USER_ID, F_GAME_ID, F_SUPER_AGENT_GAME_ID, F_IS_AUTHORIZED, F_SYS_FLAG, F_CREATE_TIME,
        F_RESOURCE_ID, F_RESOURCE_NAME, F_SYS_AGENT_ID,
        F_ACCOUNT,F_END_DATE,F_START_DATE,F_SUPER_AGENT_ID,
        UserID, GameID,ROLE_ID,ROLE_NAME,RMB,F_ORDER_NO,F_ROLE_ID,
        Gameid, qunName, Userid, AgentRoomid
    }

    /**
     * 实体类属性常量
     */
    public enum ModelFieldConstant {
        gameId, superAgentGameId, isAuthorized, resourceName, sysFlag,
        totalPayPrice, totalClearPrice,superAgentId,
        secretKey, password,favorerAccount
    }

    /**
     * 通用字段常量
     */
    public enum CommonFieldConstant {
        orderBy, groupBy,account,sysAgentId,
        startTime, endTime,
        startDate, endDate,
        gameId1, gameId2,   // 用户gameId，资源用户gameId
        t1, t2, t3,         // 直属会员，二级团队，三级团队
        isThirdJunior,      // 是否查询三级团队
        nickNameCharLength, // 昵称字符长度
        withCardNum         // 查询用户信息时是否关联房卡
    }

    /**
     * 排序常量
     */
    public static class SortConstant {
        public static final String CREATE_TIME_ASC = "F_CREATE_TIME asc";
        public static final String CREATE_TIME_DESC = "F_CREATE_TIME desc";
        public static final String CREATE_TIME_DESCS = "a.F_CREATE_TIME desc";
        public static final String CREATE_TIME_DESCB = "b.F_CREATE_TIME desc";
        public static final String MODIFY_TIME_DESC = "F_MODIFY_TIME desc";
        public static final String TRANS_MEMBER_TIME_DESC = "F_TRANS_MEMBER_TIME desc";
        public static final String END_DATE_DESC = "F_END_DATE desc";
        public static final String COLLECT_DATE_DESC = "CollectDate desc";
        public static final String SEND_TIME_DESC = "SendTime desc";
        public static final String BUILD_DATE_DESC = "BuilDate desc";
        public static final String AGENTROOMID_ASC = "AgentRoomid asc";
        public static final String LASTTIME="F_LAST_LOGIN_TIME desc";
        public static final String LOOK_CARD="RegisterDate desc";
        public static final String Collecttime_desc="Collecttime desc";
    }

    /**
     * 分组常量
     */
    public static class GroupConstant {
        public static final String CREATE_DATES = "F_CREATE_TIME,F_GAME_TYPE_ID";
        public static final String CREATE_DATED = "F_CREATE_TIME";
        public static final String CREATE_DATE= "convert(F_CREATE_TIME, date)";
        public static final String LAST_DATE="F_LAST_LOGIN_TIME";
    }


}
