package com.xiaojun.core.redis.lock;

/**
 * 有返回数据的回调函数
 * @author f00lish
 * @version 2018/7/29
 * Created By IntelliJ IDEA.
 * Qun:530350843
 */

public interface ReturnCallBack<T> {
    T execute();
}