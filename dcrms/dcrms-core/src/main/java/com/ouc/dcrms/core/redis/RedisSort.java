package com.ouc.dcrms.core.redis;

import redis.clients.jedis.Jedis;

/**
 * @author WuPing
 * @version 2017年2月23日 下午3:55:52
 */

public class RedisSort {

    public static void main(String[] args) {
	// 连接本地的 Redis 服务
	Jedis jedis = RedisUtil.getJedis();

	// jedis 排序
	// 注意：此处的 rpush 和 lpush 是 List 的操作, 是一个双向链表.
	jedis.del("a"); // 先清除数据, 再加入数据进行测试.
	jedis.rpush("a", "2");
	jedis.lpush("a", "6");
	jedis.lpush("a", "5");
	jedis.lpush("a", "10");
	System.out.println(jedis.lrange("a", 0, -1)); // [10, 5, 6, 2]
	System.out.println(jedis.sort("a")); // [2, 5, 6, 10] // 输入排序后结果
	System.out.println(jedis.lrange("a", 0, -1));
    }

}
