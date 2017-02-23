package com.ouc.dcrms.core.redis;

import redis.clients.jedis.Jedis;

/**
 * @author WuPing
 * @version 2017年2月23日 下午3:47:45
 */

public class RedisSet {

    public static void main(String[] args) {
	// 连接本地的 Redis 服务
	Jedis jedis = RedisUtil.getJedis();
	
	// 添加  
        jedis.sadd("user", "xiaobai");  
        jedis.sadd("user", "xiaowang");  
        jedis.sadd("user", "xiaowu");  
        jedis.sadd("user", "xiaozhang");
        jedis.sadd("user", "who");  
       
        // 移除 who  
        jedis.srem("user","who");
        
        System.out.println(jedis.smembers("user"));  //获取所有加入的  value  
        System.out.println(jedis.sismember("user", "who")); //判断 who 是否是 user 集合的元素  
        System.out.println(jedis.srandmember("user"));  
        System.out.println(jedis.scard("user"));  // 返回集合的元素个数  
    }

}
