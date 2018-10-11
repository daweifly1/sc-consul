package com.xiaojun.common.hystrix;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author f00lish
 * @version 2018/6/14
 * Created By IntelliJ IDEA.
 * Qun:530350843
 */


@ConfigurationProperties("hystrix.propagate.request-attribute")
public class HystrixRequestAttributeProperties {

    private boolean enabled = true;

    public boolean isEnabled() {

        return enabled;
    }

    public void setEnabled(boolean enabled) {

        this.enabled = enabled;
    }

}