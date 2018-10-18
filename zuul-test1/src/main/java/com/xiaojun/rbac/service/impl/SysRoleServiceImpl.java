package com.xiaojun.rbac.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xiaojun.rbac.beans.dt.SysPermission;
import com.xiaojun.rbac.beans.dt.SysRole;
import com.xiaojun.rbac.beans.rsp.exception.EasException;
import com.xiaojun.rbac.beans.rsp.server.Pagenation;
import com.xiaojun.rbac.beans.rsp.server.Response;
import com.xiaojun.rbac.beans.vo.TreeNode;
import com.xiaojun.rbac.mapper.SysPermissionMapper;
import com.xiaojun.rbac.mapper.SysRoleMapper;
import com.xiaojun.rbac.mapper.SysRolePermissionMapper;
import com.xiaojun.rbac.mapper.SysUserRoleMapper;
import com.xiaojun.rbac.service.ISysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class SysRoleServiceImpl implements ISysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public Pagenation roleListPage(Integer curPage, Integer pageSize) {
        Page<SysRole> sysRolePage = PageHelper.startPage(curPage, pageSize);
        List<SysRole> sysRoleList = sysRoleMapper.roleListPage();
        return new Pagenation(true, curPage, pageSize, sysRolePage.getTotal(), null, null, sysRoleList);
    }

    @Override
    public SysRole findOneById(Integer id) {
        SysRole sysRole = sysRoleMapper.findOneById(id);
        if (sysRole == null) {
            throw new EasException(404, "没有该条数据");
        }
        return sysRole;
    }

    @Override
    public List<TreeNode> allNodes(Integer roleId) {
        // 所有权限
        List<SysPermission> sysPermissionList = sysPermissionMapper.findAll();
        // 该角色所拥有的权限
        List<SysPermission> havePermissionList = sysPermissionMapper.havePermissionList(roleId);
        // 构建权限树集合
        List<TreeNode> treeNodeList = new ArrayList<>();
        for (SysPermission sysPermission : sysPermissionList) {
            TreeNode treeNode = new TreeNode();
            treeNode.setId(sysPermission.getId());
            treeNode.setParentId(sysPermission.getParentId());
            treeNode.setName(sysPermission.getName());
            // 选中状态
            for (SysPermission havePermission : havePermissionList) {
                if (sysPermission.getId() == havePermission.getId()) {
                    treeNode.setChecked(true);
                }
            }
            treeNodeList.add(treeNode);
        }
        return treeNodeList;
    }

    @Transactional
    @Override
    public Response editRole(SysRole sysRole, String permissionIds) {
        // 修改角色基本信息
        int result = sysRoleMapper.editRole(sysRole);
        // 删除该角色所拥有的权限
        sysRolePermissionMapper.deletePermissionByRoleId(sysRole.getId());
        // 为该角色赋予新的权限(这里加一个判断,因为可能一个权限也不赋予)
        if (StringUtils.isNotBlank(permissionIds)) {
            String[] permissionIdArr = permissionIds.split(",");
            sysRolePermissionMapper.assignPermission(sysRole.getId(), permissionIdArr);
        }
        if (result > 0) return Response.success("修改成功");
        return Response.error("修改失败");
    }

    @Transactional
    @Override
    public Response addRole(SysRole sysRole, String permissionIds) {
        // 添加角色基本信息
        int result = sysRoleMapper.addRole(sysRole);
        // 为该角色赋予权限(这里加一个判断,因为可能一个权限也不赋予)
        if (StringUtils.isNotBlank(permissionIds)) {
            String[] permissionIdArr = permissionIds.split(",");
            sysRolePermissionMapper.assignPermission(sysRole.getId(), permissionIdArr); // 这里的id是插入成功后返回的主键id
        }
        if (result > 0) return Response.success("添加成功");
        return Response.error("添加失败");
    }

    @Transactional
    @Override
    public Response batchDel(String ids) {
        String[] idArr = ids.split(",");
        // 系统默认角色禁止删除
        for (String id : idArr) {
            if (id.equals("1") || id.equals("2") || id.equals("3")) {
                return Response.error("系统默认角色禁止删除");
            }
        }
        // 删除角色
        int result = sysRoleMapper.batchDel(idArr);
        // 把该角色对应的中间表数据也删除掉
        for (String id : idArr) {
            sysRolePermissionMapper.deletePermissionByRoleId(Integer.valueOf(id));
            sysUserRoleMapper.deleteRoleByRoleId(Integer.valueOf(id));
        }
        if (result > 0) return Response.success("批量操作成功");
        return Response.error("批量操作失败");
    }

    @Override
    public List<SysRole> findAllByUserId(Integer userId) {
        List<SysRole> sysRoleList = sysRoleMapper.findAll();
        List<SysRole> selfSysRoleList = sysRoleMapper.findAllByUserId(userId);
        for (SysRole sysRole : sysRoleList) {
            for (SysRole selfSysRole : selfSysRoleList) {
                if (sysRole.getId() == selfSysRole.getId()) {
                    sysRole.setIsSelf(true);
                }
            }
        }
        return sysRoleList;
    }

    @Override
    public Set<String> queryAllPermitions(List<SysRole> roles) {
        int INIT = 2000;
        Set<String> result = new HashSet<>(INIT);
        // 所有权限
        List<SysPermission> sysPermissionList = sysPermissionMapper.findAll();
        Set<Integer> havePermissionList = new HashSet<>(INIT);
        for (SysRole role : roles) {
            List<SysPermission> ll = sysPermissionMapper.havePermissionList(role.getId());
            for (int i = 0; i < ll.size() && null != ll; i++) {
                havePermissionList.add(ll.get(i).getId());
            }
        }
        for (SysPermission sysPermission : sysPermissionList) {
            if (havePermissionList.contains(sysPermission.getId())) {
                result.add(sysPermission.getUrl());
            }
        }
        return result;
    }
}