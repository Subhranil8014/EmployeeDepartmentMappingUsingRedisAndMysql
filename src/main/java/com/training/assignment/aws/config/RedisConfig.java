package com.training.assignment.aws.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;


@Configuration
@EnableRedisRepositories
public class RedisConfig {
	
			@Bean
			public JedisConnectionFactory connectionFactory() {
				JedisConnectionFactory configuration=new JedisConnectionFactory();
				configuration.setHostName("testredis.ulwqem.ng.0001.aps1.cache.amazonaws.com");
				configuration.setPort(6379);
				configuration.afterPropertiesSet();
				return configuration;
			}
			
			@Bean
			public RedisTemplate<String, Object>redisTemplate(){
				 RedisTemplate<String, Object> template=new RedisTemplate<>();
				 template.setConnectionFactory(connectionFactory());
				 //template.setEnableTransactionSupport(true);
				 template.afterPropertiesSet();

				 return template;
			}


		


	}


