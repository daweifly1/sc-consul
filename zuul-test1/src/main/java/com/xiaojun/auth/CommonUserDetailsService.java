/**
 *
 */
package com.xiaojun.auth;

import com.xiaojun.rbac.api.RbacService;
import com.xiaojun.rbac.api.vo.SysUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 依赖rbac
 */
@Component
public class CommonUserDetailsService implements UserDetailsService {
    @Autowired
    private RbacService rbacService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SysUserDetail ur = rbacService.findByUsername(username);
        if (null == ur) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return ur;
    }

}
