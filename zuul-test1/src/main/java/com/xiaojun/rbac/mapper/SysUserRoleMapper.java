package com.xiaojun.rbac.mapper;

import org.apache.ibatis.annotations.Param;

public interface SysUserRoleMapper {

    int deleteRoleByUserId(Integer userId);

    void deleteRoleByRoleId(Integer roleId);

    int assignRole(@Param("userId") Integer userId, @Param("roleIds") Integer[] roleIds);
}