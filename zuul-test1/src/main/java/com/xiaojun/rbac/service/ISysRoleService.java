package com.xiaojun.rbac.service;

import com.xiaojun.rbac.beans.dt.SysRole;
import com.xiaojun.rbac.beans.rsp.server.Pagenation;
import com.xiaojun.rbac.beans.rsp.server.Response;
import com.xiaojun.rbac.beans.vo.TreeNode;

import java.util.List;

/**
 * Author: Mr.X
 * Date: 2017/12/24 上午11:41
 * Description:
 */
public interface ISysRoleService {

    Pagenation roleListPage(Integer curPage, Integer pageSize);

    SysRole findOneById(Integer id);

    List<TreeNode> allNodes(Integer roleId);

    Response editRole(SysRole sysRole, String permissionIds);

    Response addRole(SysRole sysRole, String permissionIds);

    Response batchDel(String ids);

    List<SysRole> findAllByUserId(Integer userId);
}