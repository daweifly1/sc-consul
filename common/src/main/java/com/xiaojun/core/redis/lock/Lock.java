package com.xiaojun.core.redis.lock;

import lombok.Data;

import java.util.UUID;

/**
 * @author f00lish
 * @version 2018/7/27
 * Created By IntelliJ IDEA.
 * Qun:530350843
 */
@Data
public class Lock {
    private String name;
    private String value;

    public Lock() {
    }

    public Lock(String name) {
        this.name = name;
        this.value = UUID.randomUUID().toString();
    }
}
