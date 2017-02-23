package com.ouc.dcrms.core.redis;

import redis.clients.jedis.Jedis;

/**
 * @author WuPing
 * @version 2017年2月23日 下午2:39:30
 */

public class ConnectRedis {
    public ConnectRedis() {
	// 连接本地的  Redis 服务
	@SuppressWarnings("resource")
	Jedis jedis = new Jedis(RedisUtil.ADDR, RedisUtil.PORT);
	//权限认证
	jedis.auth(RedisUtil.AUTH); 
	System.out.println("Connection to server sucessfully");
	// 查看服务是否运行
	System.out.println("Server is running: " + jedis.ping());
    }
}
