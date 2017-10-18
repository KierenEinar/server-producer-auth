package com.kangning.server.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Set;

/**
 * Created by kieren on 17/10/18.
 */
@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Resource(name = "redisTemplate")
    private ValueOperations valueOperations;
    @Resource(name = "redisTemplate")
    private ListOperations listOperations;
    @Resource(name = "redisTemplate")
    private ZSetOperations zSetOperations;
    @Resource(name = "redisTemplate")
    private SetOperations setOperations;
    public void set (final String key, final Object value) {
        valueOperations.set(key,value);
    }

    public void set (final String key, final Object value, final Long expire) {
        valueOperations.set(key, value, expire);
    }

    public Object get (final String key) {
        return valueOperations.get(key);
    }

    public Long getExpire (final String key) {
        return redisTemplate.getExpire(key);
    }

    public Long lpush (final String key, final Object value) {
        return listOperations.leftPush(key,value);
    }

    public Long lpush (final String key, final Object...values) {
        return listOperations.leftPushAll(key, values);
    }

    public Long rpush (final String key, final Object value) {
        return listOperations.leftPush(key, value);
    }

    public Long rpush (final String key,final Object...values) {
        return  listOperations.rightPushAll(key, values);
    }

    public Object lpop (final String key) {
        return listOperations.leftPop(key);
    }

    public Object rpop (final String key) {
        return listOperations.rightPop(key);
    }

    public Collection<Object> range (final String key, Integer start, Integer end) {
        return listOperations.range(key, start, end);
    }

    public Long sadd (final String key, final Object...values) {
        return setOperations.add(key, values);
    }

    public Set smembers (final String key) {
        return setOperations.members(key);
    }

    public Set sdif (final String key1, final String key2) {
        return setOperations.difference(key1, key2);
    }

    public Set sunion (final String key1, final String key2) {
        return setOperations.union(key1, key2);
    }

    public Object execute (SessionCallback sessionCallback) {
        return redisTemplate.execute(sessionCallback);
    }

}
