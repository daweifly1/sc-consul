package com.xiaojun.core.redis.lock;

/**
 * @author f00lish
 * @version 2018/7/27
 * Created By IntelliJ IDEA.
 * Qun:530350843
 */
public interface ILockHandler {


    /**
     * 操作redis获取全局锁
     *
     * @param lock           锁的名称
     * @param lockExpireTime 获取成功后锁的过期时间
     * @param timeout        获取的超时时间
     * @param tryInterval    多少ms尝试一次
     * @return true 获取成功，false获取失败
     */
    boolean tryLock(Lock lock, long lockExpireTime, long timeout, long tryInterval);


    /**
     * 尝试获取全局锁
     *
     * @param lock 锁的名称
     * @return true 获取成功，false获取失败
     */
    boolean tryLock(Lock lock);


    /**
     * 尝试获取全局锁
     *
     * @param lock           锁的名称
     * @param lockExpireTime 过期时间 单位ms
     * @return true 获取成功，false获取失败
     */
    boolean tryLock(Lock lock, long lockExpireTime);


    /**
     * 尝试获取全局锁
     *
     * @param lock           锁的名称
     * @param lockExpireTime 过期时间 单位ms
     * @param timeout        获取锁的超时时间
     * @return true 获取成功，false获取失败
     */
    boolean tryLock(Lock lock, long lockExpireTime, long timeout);


    /**
     * 释放锁
     */
    boolean releaseLock(Lock lock);
}
