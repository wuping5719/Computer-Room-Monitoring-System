package com.ouc.dcrms.core.redis;

import java.util.Date;

import com.ouc.dcrms.core.util.CheckSumBuilder;

import redis.clients.jedis.Jedis;

/**
 * @author WuPing
 * @version 2017年2月23日 下午3:05:23
 */

public class RedisString {

    public static void main(String[] args) {
	// 连接本地的  Redis 服务
	Jedis jedis = RedisUtil.getJedis();
	
	Date now = new Date();
	String username = "xiaoxue";
	String pwdStr = "xiao156W1";
	String truename = "晓雪";
	
	@SuppressWarnings("deprecation")
	String pwd = CheckSumBuilder.getCheckSum(pwdStr, "10", now.toLocaleString());
	
	// 设置 Redis 字符串数据
	jedis.set(CheckSumBuilder.getMD5(username).toUpperCase(), "username='" + username + 
		"',password='" + pwd + "',truename='" + truename + "'");
	// 获取存储的数据并输出
	System.out.println("Stored string in redis: "); 
	System.out.println("   " + jedis.get(CheckSumBuilder.getMD5(username).toUpperCase()));
    }

}
