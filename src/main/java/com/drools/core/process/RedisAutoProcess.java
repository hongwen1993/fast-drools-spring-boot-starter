package com.drools.core.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:hongwen0928@outlook.com">Karas</a>
 * @date 2020/3/14 23:36
 * @since 1.0.0
 */

@Configuration("redisAutoProcess")
@ConditionalOnClass(name = "org.springframework.data.redis.core.RedisOperations")
@AutoConfigureAfter(name = "org.springframework.data.redis.core.RedisTemplate")
public class RedisAutoProcess implements AutoProcess {

    static {
        System.out.println("加载RedisAutoProcess");
    }

    @Autowired
    private org.springframework.data.redis.core.RedisTemplate redisTemplate;

    @Override
    public Object show() {
        System.out.println("show");
        return setIfAbsent("test", "1", 15);
    }

    public Boolean setIfAbsent(String key, String value, Integer seconds) {
        return (Boolean) redisTemplate.execute((org.springframework.data.redis.core.RedisCallback) connection ->
                connection.set(key.getBytes(), value.getBytes(),
                        org.springframework.data.redis.core.types.Expiration.from(seconds, TimeUnit.SECONDS),
                        org.springframework.data.redis.connection.RedisStringCommands.SetOption.SET_IF_ABSENT));
    }
    //@Override
    //public Object invoke() {
    //    return null;
    //}
}
