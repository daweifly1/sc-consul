package com.xiaojun.core.redis.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 公共的缓存读写类
 */

@Service
public class RedisClient {

    @Autowired
    private RedisTemplate redisTemplate;

    //默认缓存时间 **一天**
    private Long defaultCacheTime = 86400L;


    /**
     * 根据传入key值 获取对象
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 批量获取value
     *
     * @param keys
     * @return
     */
    public List<Object> multiGet(List<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    /**
     * 根据key值设置value，过期时间默认一天
     *
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        set(key, value, defaultCacheTime);
    }

    /**
     * 根据key值设置value并设置过期时间
     *
     * @param key
     * @param value
     * @param timeout
     */
    public void set(String key, Object value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    /**
     * 根据key值删除缓存
     *
     * @param key
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }


    /**
     * 如果缓存中不存在改key，则添加
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setIfAbsent(String key, Object value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 设置hash值 默认过期时间**1天**
     *
     * @param key
     * @param hashKey
     * @param value
     */
    public void setHash(String key, Object hashKey, Object value) {
        setHash(key, hashKey, value, defaultCacheTime);
    }

    /**
     * 设置hash值
     *
     * @param key
     * @param hashKey
     * @param value
     * @param timeout 过期时间 单位是秒
     */
    public void setHash(String key, Object hashKey, Object value, long timeout) {
        redisTemplate.opsForHash().put(key, hashKey, value);
        if (timeout != 0) {
            redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        }
    }

    /**
     * 根据key值和hashKey值获取hash
     *
     * @param key
     * @param hashKey
     * @return
     */
    public Object getHash(String key, Object hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }


    /**
     * 根据key值和hashKey值删除hash
     *
     * @param key
     * @param hashKey
     */
    public void deleteHash(String key, Object hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }


    /**
     * 根据key值直接存map 默认过期时间为一天
     *
     * @param key
     * @param map
     */
    public void hashPutAll(String key, Map<?, ?> map) {
        hashPutAll(key, map, defaultCacheTime);

    }

    /**
     * 根据key值直接存map
     *
     * @param key
     * @param map
     * @param timeout 过期时间 单位为秒
     */
    public void hashPutAll(String key, Map<?, ?> map, long timeout) {

        redisTemplate.opsForHash().putAll(key, map);
        if (timeout != 0) {
            redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        }
    }

    /**
     * 根据key值获取map
     *
     * @param key
     * @return
     */
    public Map<?, ?> hashEntries(String key) {
        Map<?, ?> map = redisTemplate.opsForHash().entries(key);
        return map != null ? map : new HashMap();
    }

    /**
     * 删除整个hash
     *
     * @param key
     */
    public void deleteHash(String key) {
        redisTemplate.opsForHash().delete(key, null);
    }
}
