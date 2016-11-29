package com.ouc.dcrm.system.cache;

import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.ibatis.cache.Cache;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ouc.dcrm.system.util.SerializeUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author WuPing
 * @version 2016年11月29日 下午10:46:22
 */

// 使用第三方缓存服务器，处理二级缓存
public class RedisCache implements Cache {
    
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private String id;
    private JedisPool jedisPool;
    private static final int DB_INDEX = 1;
    private final String COMMON_CACHE_KEY = "COM:";
    private static final String UTF8 = "utf-8";

    private ApplicationContext context;

    // 按照一定规则标识key
    private String getKey(Object key) {
        StringBuilder accum = new StringBuilder();
        accum.append(COMMON_CACHE_KEY);
        accum.append(this.id).append(":");
        accum.append(DigestUtils.md5Hex(String.valueOf(key)));
        return accum.toString();
    }

    // redis key规则前缀
    private String getKeys() {
        return COMMON_CACHE_KEY + this.id + ":*";
    }

    public RedisCache() {}

    public RedisCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("必须传入ID");
        }
        context = new ClassPathXmlApplicationContext("spring-redis.xml");
        JedisPoolConfig jedisPoolConfig = (JedisPoolConfig) context.getBean("jedisPoolConfig");
        jedisPool = new JedisPool(jedisPoolConfig, "192.168.12.90", 7504);
        System.out.println("--MybatisRedisCache:id=" + id);
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @SuppressWarnings("deprecation")
    @Override
    public int getSize() {
        Jedis jedis = null;
        int result = 0;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = jedisPool.getResource();
            jedis.select(DB_INDEX);
            Set<byte[]> keys = jedis.keys(getKeys().getBytes(UTF8));
            if (null != keys && !keys.isEmpty()) {
                result = keys.size();
            }
            System.out.println(this.id + "-->总缓存数:" + result);
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
                jedisPool.returnBrokenResource(jedis);
        } finally {
            if (borrowOrOprSuccess)
                jedisPool.returnResource(jedis);
        }
        return result;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void putObject(Object key, Object value) {
        Jedis jedis = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = jedisPool.getResource();
            jedis.select(DB_INDEX);

            byte[] keys = getKey(key).getBytes(UTF8);
            jedis.set(keys, SerializeUtil.serialize(value));
            System.out.println("--添加缓存:" + this.id);
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
                jedisPool.returnBrokenResource(jedis);
        } finally {
            if (borrowOrOprSuccess)
                jedisPool.returnResource(jedis);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public Object getObject(Object key) {
        Jedis jedis = null;
        Object value = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = jedisPool.getResource();
            jedis.select(DB_INDEX);
            value = SerializeUtil.unSerialize(jedis.get(getKey(key).getBytes(UTF8)));
            System.out.println("--从缓存中获取:" + this.id);
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
                jedisPool.returnBrokenResource(jedis);
        } finally {
            if (borrowOrOprSuccess)
                jedisPool.returnResource(jedis);
        }
        return value;
    }

    @SuppressWarnings("deprecation")
    @Override
    public Object removeObject(Object key) {
        Jedis jedis = null;
        Object value = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = jedisPool.getResource();
            jedis.select(DB_INDEX);
            value = jedis.del(getKey(key).getBytes(UTF8));
            System.out.println("--LRU算法从缓存中移除:" + this.id);
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
                jedisPool.returnBrokenResource(jedis);
        } finally {
            if (borrowOrOprSuccess)
                jedisPool.returnResource(jedis);
        }
        return value;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void clear() {
        Jedis jedis = null;
        boolean borrowOrOprSuccess = true;
        try {
            jedis = jedisPool.getResource();
            jedis.select(DB_INDEX);
            // 如果有删除操作，会影响到整个表中的数据，因此要清空一个mapper的缓存
            // （一个mapper的不同数据操作对应不同的key）
            Set<byte[]> keys = jedis.keys(getKeys().getBytes(UTF8));
            System.out.println("--出现CUD操作, 清空对应Mapper缓存:" + keys.size());
            for (byte[] key : keys) {
                jedis.del(key);
            }
            // 下面是网上流传的方法，极大的降低系统性能，没起到加入缓存应有的作用，这是不可取的
            // jedis.flushDB();
            // jedis.flushAll();
        } catch (Exception e) {
            borrowOrOprSuccess = false;
            if (jedis != null)
                jedisPool.returnBrokenResource(jedis);
        } finally {
            if (borrowOrOprSuccess)
                jedisPool.returnResource(jedis);
        }
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }
}
