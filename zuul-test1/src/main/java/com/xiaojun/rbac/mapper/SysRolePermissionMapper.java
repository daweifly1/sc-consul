package com.xiaojun.rbac.mapper;


import org.apache.ibatis.annotations.Param;

public interface SysRolePermissionMapper {

    int deletePermissionByRoleId(Integer roleId);

    int assignPermission(@Param("roleId") Integer roleId, @Param("permissionIdArr") String[] permissionIdArr);
}