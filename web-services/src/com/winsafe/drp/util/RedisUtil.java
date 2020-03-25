package com.winsafe.drp.util;

import java.io.IOException;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
	private static JedisPool pool = null;

	static {
		String host = "redis-rtci-qa.uvws03.ng.0001.cnn1.cache.amazonaws.com.cn";
		int port = 6379;
		try {
			host = SysConfig.getSysConfig().getProperty("redis.host");
			port = Integer.valueOf(SysConfig.getSysConfig().getProperty("redis.port"));
		} catch (IOException e) {
			WfLogger.error("", e);
		}
		pool = new JedisPool(new JedisPoolConfig(), host, port);
	}
	
	public static Jedis getResource() {
		return pool.getResource();
	}
	
	public static void closePool() {
		pool.close();
	}
}
