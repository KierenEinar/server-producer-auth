package com.kangning.server.controller;

import com.google.common.collect.Lists;
import com.kangning.server.model.User;
import com.kangning.server.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

/**
 * Created by kieren on 17/10/17.
 */
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping(value = "/{key}/{value}",method = RequestMethod.POST)
    public String getPort (@PathVariable("key") String key, @PathVariable("value") String value) {
        redisUtil.set(key, value);
        return (String) redisUtil.get (key);
    }

    @RequestMapping(value = "/{key}/{name}/{age}",method = RequestMethod.POST)
    public User getUser (@PathVariable("key") String key, @PathVariable("name") String name, @PathVariable("age") Integer age) {
        User user = new User();
        user.setName(name);
        user.setAge(age);
        redisUtil.set(key,user);
        return (User)redisUtil.get(key);
    }

    @RequestMapping(value = "/{key}/{name}/{age}",method = RequestMethod.PUT)
    public Collection<User> getUsers (@PathVariable("key") String key, @PathVariable("name") String name, @PathVariable("age") Integer age) {
        List<User> users = Lists.newArrayList();
        for (Integer i=0; i<5; i++){
            User user = new User();
            user.setName(name);
            user.setAge(age);
            users.add(user);
        }
        redisUtil.lpush(key, users.toArray());
        return (Collection) redisUtil.range(key,0,-1);
    }


    @RequestMapping(value = "/{key}/{name}/{age}",method = RequestMethod.PATCH)
    public Object execute (@PathVariable("key") final String key, @PathVariable("name") final String name, @PathVariable("age") final Integer age) {
        return redisUtil.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                redisOperations.multi();
                for (Integer i=0; i<5; i++){
                    User user = new User();
                    user.setName(name);
                    user.setAge(age);
                    redisOperations.opsForList().leftPush(key,user);
                }
                redisOperations.exec();
                return redisOperations.opsForList().range(key,0,-1);
            }
        });
    }

}
