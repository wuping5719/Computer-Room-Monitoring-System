package com.ouc.dcrms.core.redis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;

/**
 * @author WuPing
 * @version 2017年2月23日 下午3:40:18
 */

public class RedisMap {

    public static void main(String[] args) {
	// 连接本地的 Redis 服务
	Jedis jedis = RedisUtil.getJedis();
		
	//-----添加数据----------  
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "xiaoxue");
        map.put("age", "24");
        map.put("qq", "15689012");
        
        
        jedis.hmset("user", map);
        // 第一个参数是存入 redis 中 map 对象的key,
        // 后面跟的是放入 map 中的对象的 key, 后面的 key 可以跟多个, 是可变参数. 
        List<String> list = jedis.hmget("user", "name", "age", "qq");
        System.out.println(list);  
  
        // 删除 map 中的某个键值  
        jedis.hdel("user", "age");
        System.out.println(jedis.hmget("user", "age")); // 删除了, 所以返回的是 null.  
        System.out.println(jedis.hlen("user"));   // 返回 key 为 user 的键中存放的值的个数2. 
        System.out.println(jedis.exists("user")); // 是否存在 key 为 user 的记录返回 true.  
        System.out.println(jedis.hkeys("user"));  // 返回  map 对象中的所有 key.  
        System.out.println(jedis.hvals("user"));  // 返回  map 对象中的所有 value. 
  
        Iterator<String> iter =jedis.hkeys("user").iterator();  
        while (iter.hasNext()){  
            String key = iter.next();  
            System.out.println(key + ": " + jedis.hmget("user", key));  
        }  
    }

}
