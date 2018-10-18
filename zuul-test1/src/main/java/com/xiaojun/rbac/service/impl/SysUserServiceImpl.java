package com.xiaojun.rbac.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xiaojun.rbac.beans.dt.SysUser;
import com.xiaojun.rbac.mapper.SysRoleMapper;
import com.xiaojun.rbac.mapper.SysUserMapper;
import com.xiaojun.rbac.mapper.SysUserRoleMapper;
import com.xiaojun.rbac.beans.rsp.exception.EasException;
import com.xiaojun.rbac.beans.rsp.server.Pagenation;
import com.xiaojun.rbac.beans.rsp.server.Response;
import com.xiaojun.rbac.service.ISysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author: Mr.X
 * Date: 2017/12/23 下午5:26
 * Description:
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public Pagenation userListPage(Integer curPage, Integer pageSize) {
        Page<SysUser> sysUserPage = PageHelper.startPage(curPage, pageSize);
        List<SysUser> sysUserList = sysUserMapper.userListPage();
        for (SysUser sysUser : sysUserList) {
            sysUser.setPassword(StringUtils.EMPTY);
            sysUser.setSysRoleList(sysRoleMapper.findAllByUserId(sysUser.getId()));
        }
        return new Pagenation(true, curPage, pageSize, sysUserPage.getTotal(), null, null, sysUserList);
    }

    @Override
    public SysUser findByUsername(String username) {
        return sysUserMapper.findByUsername(username);
    }

    @Override
    public SysUser findOneById(Integer id) {
        SysUser sysUser = sysUserMapper.findOneById(id);
        if (sysUser == null) {
            throw new EasException(404, "没有该条数据");
        }
        return sysUser;
    }

    @Transactional
    @Override
    public Response editUser(SysUser sysUser, Integer[] roleIds) {
        // 修改用户基本信息
        sysUserMapper.editUser(sysUser);
        // 删除用户所拥有的角色
        sysUserRoleMapper.deleteRoleByUserId(sysUser.getId());
        // 重新赋予用户新角色
        int result = sysUserRoleMapper.assignRole(sysUser.getId(), roleIds);
        if (result > 0) return Response.success("修改成功");
        return Response.error("修改失败");
    }

    @Transactional
    @Override
    public Response addUser(SysUser sysUser, Integer[] roleIds) {
        // 添加用户基本信息
        sysUserMapper.addUser(sysUser);
        // 赋予用户角色,注意:sysUser.getId()是插入成功后返回的主键值
        int result = sysUserRoleMapper.assignRole(sysUser.getId(), roleIds);
        if (result > 0) return Response.success("添加成功");
        return Response.error("添加失败");
    }

    @Transactional
    @Override
    public Response batchDel(String ids) {
        String[] idArr = ids.split(",");
        // 删除用户
        int result = sysUserMapper.batchDel(idArr);
        // 把该用户对应的中间表数据也删除掉
        for (String id : idArr) {
            sysUserRoleMapper.deleteRoleByUserId(Integer.valueOf(id));
        }
        if (result > 0) return Response.success("批量操作成功");
        return Response.error("批量操作失败");
    }
}