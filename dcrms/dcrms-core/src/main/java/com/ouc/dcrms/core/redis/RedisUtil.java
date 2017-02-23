package com.ouc.dcrms.core.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author WuPing
 * @version 2017年2月23日 下午2:52:28
 */

public final class RedisUtil {
    // Redis服务器IP
    public static String ADDR = "localhost";
    
    // Redis的端口号
    public static int PORT = 6379;
    
    // 访问密码
    public static String AUTH = "Tom$Wp#88";
    
    // 控制一个 pool 最多有多少个状态为 idle(空闲的) 的 jedis 实例, 默认值也是8.
    public static int MAX_IDLE = 200;
    
    public static int TIMEOUT = 10000;
    
    // 在borrow 一个 jedis 实例时, 是否提前进行  validate 操作;
    // 如果为true, 则得到的 jedis 实例均是可用的.
    public static boolean TEST_ON_BORROW = true;
    
    private static JedisPool jedisPool = null;
    
    // 初始化 Redis 连接池
    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxIdle(MAX_IDLE);
            config.setTestOnBorrow(TEST_ON_BORROW);
            jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // 获取 Redis 实例
    public synchronized static Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // 释放 Redis 资源 
    // @param jedis
    @SuppressWarnings("deprecation")
    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }
}
