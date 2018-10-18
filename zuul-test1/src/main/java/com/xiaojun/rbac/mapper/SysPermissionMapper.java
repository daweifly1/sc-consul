package com.xiaojun.rbac.mapper;

import com.xiaojun.rbac.beans.dt.SysPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysPermissionMapper {

    List<SysPermission> permissionListPage(@Param("name") String name);

    int batchDel(String[] idArr);

    List<SysPermission> havePermissionList(Integer roleId);

    int addPermission(SysPermission sysPermission);

    SysPermission findOneById(Integer id);

    int editPermission(SysPermission sysPermission);

    List<SysPermission> findAll();

    List<SysPermission> findAllByUserId(Integer userId);
}