package com.xiaojun.rbac.beans.rsp.server;

/**
 * Author: Mr.X
 * Date: 2017/12/27 下午5:28
 * Description:
 */
public class Const {

    // 验证码
    public static final String SESSION_CAPTCHA = "captcha";

    // 密码盐值
    public static final String SALT = "abcdefghijklmnopqrstuvwxyz";

    // 超级管理员
    public static final String ADMIN_USERNAME = "admin";

    public interface PermissionType {
        int NORMAL = 0;     // 正常
        int DISABLED = 1;   // 停用
        int DELETED = 2;    // 删除
    }
}