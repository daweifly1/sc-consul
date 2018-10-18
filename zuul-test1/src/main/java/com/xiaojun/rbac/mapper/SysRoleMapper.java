package com.xiaojun.rbac.mapper;

import com.xiaojun.rbac.beans.dt.SysRole;

import java.util.List;

public interface SysRoleMapper {

    List<SysRole> roleListPage();

    SysRole findOneById(Integer id);

    int editRole(SysRole sysRole);

    int addRole(SysRole sysRole);

    int batchDel(String[] idArr);

    List<SysRole> findAll();

    List<SysRole> findAllByUserId(Integer userId);
}