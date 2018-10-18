package com.xiaojun.rbac.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xiaojun.rbac.beans.dt.SysPermission;
import com.xiaojun.rbac.mapper.SysPermissionMapper;
import com.xiaojun.rbac.beans.rsp.exception.EasException;
import com.xiaojun.rbac.beans.rsp.server.Pagenation;
import com.xiaojun.rbac.beans.rsp.server.Response;
import com.xiaojun.rbac.service.ISysPermissionService;
import com.xiaojun.rbac.beans.vo.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Mr.X
 * Date: 2017/12/24 下午2:52
 * Description:
 */
@Service
public class SysPermissionServiceImpl implements ISysPermissionService {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public Pagenation permissionListPage(Integer curPage, Integer pageSize, String name) {
        Page<SysPermission> sysPermissionPage = PageHelper.startPage(curPage, pageSize);
        List<SysPermission> sysPermissionList = sysPermissionMapper.permissionListPage(name);
        return new Pagenation(true, curPage, pageSize, sysPermissionPage.getTotal(), null, null, sysPermissionList);
    }

    @Override
    public SysPermission findOneById(Integer id) {
        SysPermission sysPermission = sysPermissionMapper.findOneById(id);
        if (sysPermission == null) {
            throw new EasException(404, "没有该条数据");
        }
        return sysPermission;
    }

    @Override
    public List<TreeNode> allNodes() {
        List<SysPermission> sysPermissionList = sysPermissionMapper.findAll();
        List<TreeNode> treeNodeList = new ArrayList<>();
        for (SysPermission sysPermission : sysPermissionList) {
            TreeNode treeNode = new TreeNode();
            treeNode.setId(sysPermission.getId());
            treeNode.setParentId(sysPermission.getParentId());
            treeNode.setName(sysPermission.getName());
            treeNodeList.add(treeNode);
        }
        return treeNodeList;
    }

    @Transactional
    @Override
    public Response editPermission(SysPermission sysPermission) {
        int result = sysPermissionMapper.editPermission(sysPermission);
        if (result > 0) return Response.success("修改成功");
        return Response.error("修改失败");
    }

    @Transactional
    @Override
    public Response addPermission(SysPermission sysPermission) {
        int result = sysPermissionMapper.addPermission(sysPermission);
        if (result > 0) return Response.success("添加成功");
        return Response.error("添加失败");
    }

    @Transactional
    @Override
    public Response batchDel(String ids) {
        int result = sysPermissionMapper.batchDel(ids.split(","));
        if (result > 0) return Response.success("批量操作成功");
        return Response.error("批量操作失败");
    }

    @Override
    public List<SysPermission> findAll() {
        return sysPermissionMapper.findAll();
    }

    @Override
    public List<SysPermission> findAllByUserId(Integer userId) {
        return sysPermissionMapper.findAllByUserId(userId);
    }
}