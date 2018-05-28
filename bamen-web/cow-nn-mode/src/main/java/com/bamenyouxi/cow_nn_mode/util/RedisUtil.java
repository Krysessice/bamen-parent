package com.bamenyouxi.cow_nn_mode.util;

import com.bamenyouxi.core.constant.RedisConstant;
import com.bamenyouxi.core.util.AbstractRedisUtil;
import com.bamenyouxi.cow_nn_mode.model.mysql.cow_nn_mode.SystemInfo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public final class RedisUtil extends AbstractRedisUtil {

    /**
     * 获取存储系统信息的数据库引擎
     * @return  redis 存储SystemInfo的数据库引擎
     */
    public RedisTemplate<String, SystemInfo> getSystemInfoDB() {
        return super.getInstanceN(RedisConstant.DataBase.SYSTEM_INFO_DB);
    }

    /**
     * 存储 更新系统数据
     * @param systemInfo    com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.SystemInfo
     */
    public void saveSystemInfo(SystemInfo systemInfo){
         getSystemInfoDB().opsForValue().set(SystemInfo.class.getName(),systemInfo);
    }

    /**
     * 从redis中获取数据
     * @return  com.bamenyouxi.room_card_mode.model.mysql.room_card_mode.SystemInfo
     */
    public SystemInfo getSystemInfo(){
        return getSystemInfoDB().opsForValue().get(SystemInfo.class.getName());
    }
}
