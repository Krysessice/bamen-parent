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
        F_RESOURCE_ID, F_RESOURCE_NAME,
        UserID, GameID
    }

    /**
     * 实体类属性常量
     */
    public enum ModelFieldConstant {
        gameId, superAgentGameId, isAuthorized, resourceName, sysFlag,
        totalPayPrice, totalClearPrice,
        secretKey, password
    }

    /**
     * 通用字段常量
     */
    public enum CommonFieldConstant {
        orderBy, groupBy,
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
        public static final String CREATE_DATE_DESC = "convert(F_CREATE_TIME, date) desc";
        public static final String MODIFY_TIME_DESC = "F_MODIFY_TIME desc";
        public static final String TRANS_MEMBER_TIME_DESC = "F_TRANS_MEMBER_TIME desc";
        public static final String END_DATE_DESC = "F_END_DATE desc";
        public static final String COLLECT_DATE_DESC = "CollectDate desc";
    }

    /**
     * 分组常量
     */
    public static class GroupConstant {
        public static final String CREATE_DATE = "convert(F_CREATE_TIME, date)";
    }

}
