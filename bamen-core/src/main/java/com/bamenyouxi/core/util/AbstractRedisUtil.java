package com.bamenyouxi.core.util;

import com.bamenyouxi.core.constant.RedisConstant;
import com.bamenyouxi.core.constant.TipMsgConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;

/**
 * redis util
 * Created by 13477 on 2017/7/17.
 */
public abstract class AbstractRedisUtil {
	@Autowired
	private RedisTemplate<?, ?> redisTemplate;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	private RedisSentinelConfiguration getRedisSentinelConfiguration(int DataBaseIndex) {
		RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
		redisSentinelConfiguration.setDatabase(DataBaseIndex);
		return redisSentinelConfiguration;
	}

	@SuppressWarnings("unchecked")
	private <k, v> RedisTemplate<k, v> changeDB(int DataBaseIndex) {
		redisTemplate.setConnectionFactory(new JedisConnectionFactory(getRedisSentinelConfiguration(DataBaseIndex)));
		return (RedisTemplate<k, v>) redisTemplate;
	}

	/**
	 * 获取默认数据库引擎
	 * @return  RedisTemplate
	 */
	public RedisTemplate<?, ?> getInstance() {
		return redisTemplate;
	}

	/**
	 * 获取指定数据库引擎
	 * @param DataBaseIndex 数据库索引，从1开始
	 * @param <k>   键类型
	 * @param <v>   值类型
	 * @return  RedisTemplate
	 */
	protected <k, v> RedisTemplate<k, v> getInstanceN(int DataBaseIndex) {
		Assert.isTrue(DataBaseIndex > 0, TipMsgConstant.PARAM_INVALID);
		return this.changeDB(DataBaseIndex - 1);
	}

	/**
	 * 存储用户盐值
	 * @param username  用户名(gameId)
	 * @param salt      盐值(secretKey)
	 */
	public void saveSalt(String username, String salt) {
		stringRedisTemplate.opsForValue().set(RedisConstant.SALT_PREFIX + username, salt);
	}
}
