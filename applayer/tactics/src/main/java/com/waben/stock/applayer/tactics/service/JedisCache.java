package com.waben.stock.applayer.tactics.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

@Component
public class JedisCache {

	@Value("${redis.host}")
	private String redisHost;

	@Value("${redis.port}")
	private String redisPort;

	@Value("${redis.password}")
	private String redisPassword;

	private static final String Token_Cache_Key = "token:app:%s";

	private JedisPool pool;

	@PostConstruct
	public void initJedisPool() {
		if (redisPassword != null && !"".equals(redisPassword.trim())) {
			pool = new JedisPool(new JedisPoolConfig(), redisHost, Integer.parseInt(redisPort),
					Protocol.DEFAULT_TIMEOUT, redisPassword);
		} else {
			pool = new JedisPool(new JedisPoolConfig(), redisHost, Integer.parseInt(redisPort));
		}
	}

	public String getToken(String phone) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			return jedis.get(String.format(Token_Cache_Key, phone));
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public void setToken(String phone, String token) {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
			jedis.set(String.format(Token_Cache_Key, phone), token);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public JedisPool getPool() {
		return pool;
	}

}
