package com.xiaojun.core.redis.lock.impl;

import com.xiaojun.core.redis.dao.RedisDao;
import com.xiaojun.core.redis.lock.ILockHandler;
import com.xiaojun.core.redis.lock.Lock;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author f00lish
 * @version 2018/7/27
 * Created By IntelliJ IDEA.
 * Qun:530350843
 */
@Slf4j
public class DistributedLockHandler implements ILockHandler {

    private final static long LOCK_EXPIRE = 15 * 1000L; //单个业务持有锁的时间15s,防止死锁

    private final static long LOCK_TRY_INTERVAL = 30L; //默认30ms尝试一次

    private final static long LOCK_TRY_TIMEOUT = 15 * 1000L; // 默认尝试15s

    private RedisDao redisDao;

    public DistributedLockHandler(RedisDao redisDao) {
        this.redisDao = redisDao;
    }

    /**
     * 操作redis获取全局锁
     *
     * @param lock           锁的名称
     * @param lockExpireTime 获取成功后锁的过期时间
     * @param timeout        获取的超时时间
     * @param tryInterval    多少ms尝试一次
     * @return true 获取成功，false获取失败
     */
    public boolean tryLock(Lock lock, long lockExpireTime, long timeout, long tryInterval) {
        if (lock == null)
            return false;
        try {
            if (StringUtils.isEmpty(lock.getName()) || StringUtils.isEmpty(lock.getValue())) {
                return false;
            }
            long startTime = System.currentTimeMillis();
            while (true) {
                boolean result = this.getLock(lock.getName(), lock.getValue(), lockExpireTime);
                if (result) {
                    log.info(Thread.currentThread().getName() + " : get lock  success");
                    return true;
                } else {
                    log.info(Thread.currentThread().getName() + " : ----> locking is exist!!!");
                }
                if (System.currentTimeMillis() - startTime > timeout) {
                    return false;
                }
                Thread.sleep(tryInterval);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }


    /**
     * 尝试获取全局锁
     *
     * @param lock 锁的名称
     * @return true 获取成功，false获取失败
     */
    public boolean tryLock(Lock lock) {
        return tryLock(lock, LOCK_EXPIRE);
    }


    /**
     * 尝试获取全局锁
     *
     * @param lock           锁的名称
     * @param lockExpireTime 获取超时时间 单位ms
     * @return true 获取成功，false获取失败
     */
    public boolean tryLock(Lock lock, long lockExpireTime) {
        return tryLock(lock, lockExpireTime, LOCK_TRY_TIMEOUT);
    }


    /**
     * 尝试获取全局锁
     *
     * @param lock           锁的名称
     * @param lockExpireTime 获取超时时间
     * @param timeout        获取锁的超时时间
     * @return true 获取成功，false获取失败
     */
    public boolean tryLock(Lock lock, long lockExpireTime, long timeout) {
        return tryLock(lock, lockExpireTime, timeout, LOCK_TRY_INTERVAL);
    }


    public boolean getLock(String key, Object value, Long expireTime) {
        String lockScript = "local key     = KEYS[1]\n" +
                "local content = KEYS[2]\n" +
                "local ttl     = ARGV[1]\n" +
                "local lockSet = redis.call('set', key, content,'nx','px',ttl)\n" +
                "if lockSet then\n" +
                "   lockSet = 1;\n" +
                "else\n" +
                "  local value = redis.call('get', key)\n" +
                "  if(value == content) then\n" +
                "    lockSet = 1;\n" +
                "    redis.call('pexpire', key, ttl)\n" +
                "   else \n" +
                "    lockSet = 0;" +
                "  end\n" +
                "end\n" +
                "return lockSet";
        List<Object> objList = new ArrayList<Object>();
        objList.add(key);
        objList.add(value);
        Long result = (Long) redisDao.eval(lockScript, Long.class, objList, String.valueOf(expireTime));
        if (result == 1) {
            return true;
        }
        return false;
    }


    /**
     * 释放锁
     */
    public boolean releaseLock(Lock lock) {
        if (lock != null) {
            String script = "local value = redis.call('get', KEYS[1])\n" +
                    "if value == '\"'..ARGV[1]..'\"' then" +
                    " return redis.call('del', KEYS[1])" +
                    " else return 0" +
                    " end";
            List<Object> keyList = new ArrayList<Object>();
            keyList.add(lock.getName());
            Object result = redisDao.eval(script, Long.class, keyList, lock.getValue());
            Long RELEASE_SUCCESS = 1L;
            if (RELEASE_SUCCESS.equals(result)) {
                log.info(Thread.currentThread().getName() + " : del lock success");
                return true;
            }
        }
        log.info(Thread.currentThread().getName() + " : del lock fail");
        return false;
    }
}
