package com.xiaojun.core.redis.lock;

/**
 * @author f00lish
 * @version 2018/7/29
 * Created By IntelliJ IDEA.
 * Qun:530350843
 */
public interface ILockManager {
    /**
     * 通过加锁安全执行程序，进入锁返回真
     * @param keyName key名称
     * @param callback
     * @return
     */
    boolean lockCallBack(String keyName, SimpleCallBack callback);

    /**
     * 通过加锁安全执行程序，进入锁返回真
     * @param keyName
     * @param timeout
     * @param callback
     * @return
     */
    boolean lockCallBack(String keyName, long timeout, SimpleCallBack callback);

    /**
     * 通过加锁安全执行程序，进入锁返回真
     * @param keyName
     * @param timeout
     * @param tryInterval
     * @param lockExpireTime
     * @param callback
     * @return
     */
    boolean lockCallBack(String keyName, long timeout, long tryInterval, long lockExpireTime, SimpleCallBack callback);

    /**
     * 通过加锁安全执行程序，有返回数据
     * @param keyName
     * @param callback
     * @return
     */
    <T> T lockCallBackWithRtn(String keyName, ReturnCallBack<T> callback);


    /**
     * 通过加锁安全执行程序，有返回数据
     * @param keyName
     * @param timeout
     * @param callback
     * @param <T>
     * @return
     */
    <T> T lockCallBackWithRtn(String keyName, long timeout, ReturnCallBack<T> callback);

    /**
     * 通过加锁安全执行程序，有返回数据
     * @param keyName
     * @param timeout
     * @param tryInterval
     * @param lockExpireTime
     * @param callback
     * @param <T>
     * @return
     */
    <T> T lockCallBackWithRtn(String keyName, long timeout, long tryInterval, long lockExpireTime, ReturnCallBack<T> callback);

}