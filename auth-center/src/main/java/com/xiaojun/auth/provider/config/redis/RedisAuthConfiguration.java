package com.xiaojun.auth.provider.config.redis;

import com.xiaojun.auth.api.config.RedisObjectSerializer;
import com.xiaojun.user.api.model.BaseModuleResources;
import com.xiaojun.user.api.model.BaseRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created by fp295 on 2018/4/12.
 * Redis配置类
 */
@Configuration
public class RedisAuthConfiguration {

    @Autowired
    RedisTemplate redisTemplate;

    @Bean
    public RedisTemplate<String, BaseRole> baseRoleTemplate() {
        RedisTemplate<String, BaseRole> template = new RedisTemplate();
        template.setConnectionFactory(redisTemplate.getConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new RedisObjectSerializer());
        return template;
    }

    @Bean
    public RedisTemplate<String, BaseModuleResources> baseModelTemplate() {
        RedisTemplate<String, BaseModuleResources> template = new RedisTemplate();
        template.setConnectionFactory(redisTemplate.getConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new RedisObjectSerializer());
        return template;
    }
}
