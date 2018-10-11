package com.xiaojun.auth.provider.config.auth.provider;


import com.xiaojun.auth.provider.config.auth.filter.MyLoginAuthenticationFilter;
import com.xiaojun.auth.provider.config.auth.token.MyAuthenticationToken;
import com.xiaojun.auth.provider.service.BaseUserDetailService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//import org.springframework.security.authentication.dao.SaltSource;
//import org.springframework.security.authentication.encoding.PasswordEncoder;
//import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;

/**
 * Created by fp295 on 2018/6/16.
 * 自定义密码验证
 * 实现自定义的 MyAbstractUserDetailsAuthenticationProvider 抽象类
 * 根据登陆的类型 执行不同的校验
 */
public class MyAuthenticationProvider extends MyAbstractUserDetailsAuthenticationProvider {
    private PasswordEncoder passwordEncoder;
    //    private SaltSource saltSource;
    private UserDetailsService userDetailsService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, MyAuthenticationToken authentication) throws AuthenticationException {
        Object salt = null;
//        if (this.saltSource != null) {
//            salt = this.saltSource.getSalt(userDetails);
//        }

        if (authentication.getCredentials() == null) {
            this.logger.debug("Authentication failed: no credentials provided");
            throw new BadCredentialsException(this.messages.getMessage("MyAbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        } else {
            String presentedPassword = authentication.getCredentials().toString();

            // 验证开始
            if (MyLoginAuthenticationFilter.SPRING_SECURITY_RESTFUL_TYPE_PHONE.equals(authentication.getType())) {
                // 验证码验证，调用公共服务查询 key 为authentication.getPrincipal()的value， 并判断其与验证码是否匹配
                if (!"1000".equals(presentedPassword)) {
                    this.logger.debug("Authentication failed: verifyCode does not match stored value");
                    throw new BadCredentialsException(this.messages.getMessage("MyAbstractUserDetailsAuthenticationProvider.badCredentials", "Bad verifyCode"));
                }
            } else if (MyLoginAuthenticationFilter.SPRING_SECURITY_RESTFUL_TYPE_QR.equals(authentication.getType())) {
                // 二维码只需要根据 SPRING_SECURITY_RESTFUL_QR_CODE_KEY 查询到用户即可，所以此处无需验证
            } else {
                // 用户名密码验证
                if (!passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
                    this.logger.debug("Authentication failed: password does not match stored value");
                    throw new BadCredentialsException(this.messages.getMessage("MyAbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
                }
//                // 用户名密码验证
//                if (!this.passwordEncoder.isPasswordValid(userDetails.getPassword(), presentedPassword, salt)) {
//                    this.logger.debug("Authentication failed: password does not match stored value");
//                    throw new BadCredentialsException(this.messages.getMessage("MyAbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
//                }
            }
        }
    }

    @Override
    protected UserDetails retrieveUser(String username, MyAuthenticationToken authentication) throws AuthenticationException {
        UserDetails loadedUser;
        try {
            // 调用loadUserByUsername时加入type前缀
            loadedUser = userDetailsService.loadUserByUsername(authentication.getType() + "&:@" + username);
        } catch (UsernameNotFoundException var6) {
//            if (authentication.getCredentials() != null) {
////                String presentedPassword = authentication.getCredentials().toString();
////                isPasswordValid(passwordEncoder, this.userNotFoundEncodedPassword, presentedPassword, (Object) null);
//            }
            throw var6;
        } catch (Exception var7) {
            throw new InternalAuthenticationServiceException(var7.getMessage(), var7);
        }

        if (loadedUser == null) {
            throw new InternalAuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
        } else {
            return loadedUser;
        }
    }

    public void setUserDetailsService(BaseUserDetailService baseUserDetailService) {
        this.userDetailsService = userDetailsService;
    }

    public void setPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.passwordEncoder = bCryptPasswordEncoder;
    }
}
