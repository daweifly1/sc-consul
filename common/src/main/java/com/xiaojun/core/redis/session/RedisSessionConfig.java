package com.xiaojun.core.redis.session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieHttpSessionStrategy;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HttpSessionStrategy;

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600, redisNamespace = "xcloud")
public class RedisSessionConfig {

    @Bean
    public HttpSessionStrategy httpSessionStrategy() {
        //通过cookie传递sessionid
        HttpSessionStrategy httpSessionStrategy = new CookieHttpSessionStrategy();
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("JSESSIONID");
        serializer.setCookiePath("/");
        serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");
        ((CookieHttpSessionStrategy) httpSessionStrategy).setCookieSerializer(serializer);
        return httpSessionStrategy;
    }
}
