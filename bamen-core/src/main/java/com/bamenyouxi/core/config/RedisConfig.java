package com.bamenyouxi.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * RedisConfig
 * Created by 13477 on 2017/7/17.
 */
@Configuration
class RedisConfig<k, v> {

	@Autowired
	private RedisTemplate<k, v> redisTemplate;

	@Bean
	RedisTemplate<k, v> getRedisTemplate() {
		final RedisTemplate<k, v> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(new JedisConnectionFactory());
		return redisTemplate;
	}

}
