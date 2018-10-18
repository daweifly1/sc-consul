package com.xiaojun.rbac.service;

import com.xiaojun.rbac.beans.dt.SysPermission;
import com.xiaojun.rbac.beans.rsp.server.Pagenation;
import com.xiaojun.rbac.beans.rsp.server.Response;
import com.xiaojun.rbac.beans.vo.TreeNode;

import java.util.List;

/**
 * Author: Mr.X
 * Date: 2017/12/24 下午2:51
 * Description:
 */
public interface ISysPermissionService {

    Pagenation permissionListPage(Integer curPage, Integer pageSize, String name);

    SysPermission findOneById(Integer id);

    List<TreeNode> allNodes();

    Response editPermission(SysPermission sysPermission);

    Response addPermission(SysPermission sysPermission);

    Response batchDel(String ids);

    List<SysPermission> findAll();

    List<SysPermission> findAllByUserId(Integer userId);
}