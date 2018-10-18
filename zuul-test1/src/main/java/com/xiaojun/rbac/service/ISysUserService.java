package com.xiaojun.rbac.service;

import com.xiaojun.rbac.beans.dt.SysUser;
import com.xiaojun.rbac.beans.rsp.server.Pagenation;
import com.xiaojun.rbac.beans.rsp.server.Response;

public interface ISysUserService {

    Pagenation userListPage(Integer curPage, Integer pageSize);

    SysUser findByUsername(String username);

    SysUser findOneById(Integer id);

    Response editUser(SysUser sysUser, Integer[] roleIds);

    Response addUser(SysUser sysUser, Integer[] roleIds);

    Response batchDel(String ids);
}