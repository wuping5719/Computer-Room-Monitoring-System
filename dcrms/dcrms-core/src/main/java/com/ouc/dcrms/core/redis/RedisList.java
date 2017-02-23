package com.ouc.dcrms.core.redis;

import java.util.List;

import redis.clients.jedis.Jedis;

/**
 * @author WuPing
 * @version 2017年2月23日 下午3:34:31
 */

public class RedisList {

    public static void main(String[] args) {
	// 连接本地的 Redis 服务
	Jedis jedis = RedisUtil.getJedis();

	// 存储数据到列表中
	jedis.lpush("dcrms-list", "Redis");
	jedis.lpush("dcrms-list", "Mongodb");
	jedis.lpush("dcrms-list", "Mysql");
	// 获取存储的数据并输出
	List<String> list = jedis.lrange("dcrms-list", 0, 5);
	for (int i = 0; i < list.size(); i++) {
	    System.out.println("Stored list in redis: " + list.get(i));
	}
    }

}
