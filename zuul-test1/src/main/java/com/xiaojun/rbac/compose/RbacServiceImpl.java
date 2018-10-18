/**
 *
 */
package com.xiaojun.rbac.compose;

import com.xiaojun.rbac.api.RbacService;
import com.xiaojun.rbac.api.vo.SysUserDetail;
import com.xiaojun.rbac.beans.dt.SysRole;
import com.xiaojun.rbac.beans.dt.SysUser;
import com.xiaojun.rbac.service.ISysRoleService;
import com.xiaojun.rbac.service.ISysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@Component("rbacService")
public class RbacServiceImpl implements RbacService {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysRoleService sysRoleService;

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();

        boolean hasPermission = false;

        if (principal instanceof SysUserDetail) {
            //如果用户名是admin，就永远返回true
            if (StringUtils.equals(((SysUserDetail) principal).getUsername(), "admin")) {
                hasPermission = true;
            } else {
                // 读取用户所拥有权限的所有URL
                Set<String> urls = ((SysUserDetail) principal).getHavePermissionList();
                for (String url : urls) {
                    if (antPathMatcher.match(url, request.getRequestURI())) {
                        hasPermission = true;
                        break;
                    }
                }
            }
        }

        return hasPermission;
    }

    @Override
    public SysUserDetail findByUsername(String username) {
        SysUser su = sysUserService.findByUsername(username);
        if (null != su) {
            List<SysRole> rs = sysRoleService.findAllByUserId(su.getId());
            SysUserDetail sysUserDetail = new SysUserDetail();
            sysUserDetail.setPassword(su.getPassword());
            sysUserDetail.setUsername(username);
            if (!CollectionUtils.isEmpty(rs)) {
                sysUserDetail.setHavePermissionList(sysRoleService.queryAllPermitions(rs));
            }
            return sysUserDetail;
        }
        return null;
    }

}
