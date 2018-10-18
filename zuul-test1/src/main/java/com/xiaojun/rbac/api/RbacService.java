/**
 *
 */
package com.xiaojun.rbac.api;

import com.xiaojun.rbac.api.vo.SysUserDetail;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface RbacService {
    boolean hasPermission(HttpServletRequest request, Authentication authentication);

    SysUserDetail findByUsername(String username);
}
