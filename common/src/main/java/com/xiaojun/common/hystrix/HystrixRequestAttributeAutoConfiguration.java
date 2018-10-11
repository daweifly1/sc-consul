package com.xiaojun.common.hystrix;

import com.netflix.hystrix.Hystrix;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author f00lish
 * @version 2018/6/14
 * Created By IntelliJ IDEA.
 * Qun:530350843
 */


@Configuration
@ConditionalOnClass({ Hystrix.class })
@EnableConfigurationProperties(HystrixRequestAttributeProperties.class)
public class HystrixRequestAttributeAutoConfiguration {

    @Bean
    public RequestAttributeHystrixConcurrencyStrategy hystrixRequestAutoConfiguration() {

        return new RequestAttributeHystrixConcurrencyStrategy();
    }

}
