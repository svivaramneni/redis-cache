package com.successfactors.redis;

import java.util.Base64;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class SpringRedisConfig {

	@Bean
	public JedisConnectionFactory connectionFactory() {
		JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
		connectionFactory.setHostName("10.14.67.156");
		//connectionFactory.setHostName("jyom00637835a.amer.global.corp.sap");
		connectionFactory.setPort(6379);

	/*	connectionFactory.setHostName("10.170.36.87");
		//connectionFactory.setHostName("jyom00637835a.amer.global.corp.sap");
		connectionFactory.setPort(8080);*/

		return connectionFactory;
	}

	@Bean
	public RedisTemplate<String, byte[]> redisTemplate() {
		RedisTemplate<String, byte[]> redisTemplate = new RedisTemplate<String, byte[]>();
		redisTemplate.setConnectionFactory(connectionFactory());
		redisTemplate.setEnableDefaultSerializer(false);
		//redisTemplate.setKeySerializer(new StringRedisSerializer());

		//redisTemplate.getConnectionFactory().getConnection().getNativeConnection(); //This returns native connection.
		return redisTemplate;
	}

}
