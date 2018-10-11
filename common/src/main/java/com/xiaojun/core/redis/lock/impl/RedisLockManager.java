package com.xiaojun.core.redis.lock.impl;

import com.xiaojun.core.redis.dao.RedisDao;
import com.xiaojun.core.redis.lock.ILockHandler;
import com.xiaojun.core.redis.lock.ILockManager;
import com.xiaojun.core.redis.lock.Lock;
import com.xiaojun.core.redis.lock.ReturnCallBack;
import com.xiaojun.core.redis.lock.SimpleCallBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;

/**
 * @author f00lish
 * @version 2018/7/29
 * Created By IntelliJ IDEA.
 * Qun:530350843
 */
@Component
public class RedisLockManager implements ILockManager {

    @Autowired
    private RedisDao redisDao;

    protected ILockHandler distributeLock; // 分布锁

    @PostConstruct
    public void init() {
        // 初始化锁
        distributeLock = new DistributedLockHandler(redisDao);
    }

    @Override
    public boolean lockCallBack(String keyName, SimpleCallBack simpleCallBack) {
        Assert.notNull("keyName", "keyName 不能为空");
        Assert.notNull("callback", "simpleCallBack 不能为空");
        Lock lock = new Lock(keyName);
        try {
            // 获取锁
            if (distributeLock.tryLock(lock)) {
                simpleCallBack.execute();
                return true;
            }
        } finally {
            // 必须释放锁
            distributeLock.releaseLock(lock);
        }
        return false;
    }

    @Override
    public boolean lockCallBack(String keyName, long lockExpireTime, SimpleCallBack simpleCallBack) {
        Assert.notNull("keyName", "keyName 不能为空");
        Assert.notNull("lockExpireTime", "lockExpireTime 不能为空");
        Assert.notNull("simpleCallBack", "simpleCallBack 不能为空");
        Lock lock = new Lock(keyName);
        try {
            // 获取锁
            if (distributeLock.tryLock(lock, lockExpireTime)) {
                simpleCallBack.execute();
                return true;
            }
        } finally {
            // 必须释放锁
            distributeLock.releaseLock(lock);
        }
        return false;
    }

    @Override
    public boolean lockCallBack(String keyName, long lockExpireTime, long timeout, long tryInterval, SimpleCallBack simpleCallBack) {
        Assert.notNull("keyName", "keyName 不能为空");
        Assert.notNull("lockExpireTime", "lockExpireTime 不能为空");
        Assert.notNull("timeout", "timeout 不能为空");
        Assert.notNull("tryInterval", "tryInterval 不能为空");
        Assert.notNull("simpleCallBack", "simpleCallBack 不能为空");
        Lock lock = new Lock(keyName);
        try {
            // 获取锁
            if (distributeLock.tryLock(lock, timeout, tryInterval, lockExpireTime)) {
                simpleCallBack.execute();
                return true;
            }
        } finally {
            // 必须释放锁
            distributeLock.releaseLock(lock);
        }
        return false;
    }

    @Override
    public <T> T lockCallBackWithRtn(String keyName, ReturnCallBack<T> returnCallBack) {
        Assert.notNull("keyName", "keyName 不能为空");
        Assert.notNull("returnCallBack", "returnCallBack 不能为空");
        Lock lock = new Lock(keyName);
        try {
            // 获取锁
            if (distributeLock.tryLock(lock))
                return returnCallBack.execute();
        } finally {
            // 必须释放锁
            distributeLock.releaseLock(lock);
        }
        return null;
    }

    @Override
    public <T> T lockCallBackWithRtn(String keyName, long lockExpireTime, ReturnCallBack<T> returnCallBack) {
        Assert.notNull("keyName", "keyName 不能为空");
        Assert.notNull("lockExpireTime", "lockExpireTime 不能为空");
        Assert.notNull("returnCallBack", "returnCallBack 不能为空");
        Lock lock = new Lock(keyName);
        try {
            // 获取锁
            if (distributeLock.tryLock(lock, lockExpireTime))
                return returnCallBack.execute();
        } finally {
            // 必须释放锁
            distributeLock.releaseLock(lock);
        }
        return null;
    }

    @Override
    public <T> T lockCallBackWithRtn(String keyName, long lockExpireTime, long timeout, long tryInterval, ReturnCallBack<T> returnCallBack) {
        Assert.notNull("keyName", "keyName 不能为空");
        Assert.notNull("lockExpireTime", "lockExpireTime 不能为空");
        Assert.notNull("timeout", "timeout 不能为空");
        Assert.notNull("tryInterval", "tryInterval 不能为空");
        Assert.notNull("returnCallBack", "returnCallBack 不能为空");
        Lock lock = new Lock(keyName);
        try {
            // 获取锁
            if (distributeLock.tryLock(lock, timeout, tryInterval, lockExpireTime))
                return returnCallBack.execute();
        } finally {
            // 必须释放锁
            distributeLock.releaseLock(lock);
        }
        return null;
    }
}




