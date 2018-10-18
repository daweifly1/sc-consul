package com.xiaojun.rbac.mapper;


import com.xiaojun.rbac.beans.dt.SysUser;

import java.util.List;

public interface SysUserMapper {

    List<SysUser> userListPage();

    SysUser findByUsername(String username);

    SysUser findOneById(Integer id);

    int editUser(SysUser sysUser);

    int addUser(SysUser sysUser);

    int batchDel(String[] split);
}