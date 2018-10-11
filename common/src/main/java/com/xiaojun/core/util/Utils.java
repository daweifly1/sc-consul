package com.xiaojun.core.util;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaojun.common.auth.entity.IUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @author f00lish
 * @version 2018/5/6
 * Created By IntelliJ IDEA.
 * Qun:530350843
 */
@Slf4j
public class Utils {

    private static ObjectMapper objectMapper;
    private static String injector;
    private static boolean isLogicDelete;
    private static String[] ignoreList = {"currentUser", "currentDataRule"};


    /**
     * 直接使用response向浏览器写json
     *
     * @param responseMessage
     */
    public static void responseJson(Object responseMessage) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes.getResponse();
        PrintWriter out = null;
        try {
            response.setContentType("application/json;charset=UTF-8");
            String json = getObjectMapper().writeValueAsString(responseMessage);
            out = response.getWriter();
            out.write(json);    //写给response
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(out);
        }
    }

    /**
     * 全局获取httpRequest
     *
     * @return
     */

    public static HttpServletRequest getHttpServletRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取当前用户认证信息
     *
     * @return
     */
    public static Object getPrincipal() {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return principal;
        } catch (NullPointerException e) {
            log.error(e.toString());
        }
        return null;
    }

    /**
     * 获取当前用户名
     *
     * @return
     */
    public static String getPrincipalUsername() {
        Object principal = Utils.getPrincipal();
        if (principal != null) {
            if (IUser.class.isAssignableFrom(principal.getClass())) {
                return ((IUser) principal).getUsername();
            } else {
                return principal.toString();
            }
        }
        return null;
    }


    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.contains(",")) {
            String[] ips = ip.split(",");
            if (ips.length > 0)
                ip = ips[0];
        }
        return ip;
    }

    /**
     * 是否拥有此权限
     *
     * @param authorities
     * @param role
     * @return
     */
    public static boolean hasRole(Collection<? extends GrantedAuthority> authorities, String role) {
        if (CollectionUtils.isEmpty(authorities)) {
            return false;
        }
        GrantedAuthority authority = authorities.stream()
                .filter(auth -> auth.getAuthority().equals(role)).findAny()
                .orElse(null);
        return Objects.nonNull(authority);
    }


    /**
     * 获取 ObjectMapper对象用于json操作
     *
     * @return
     */
    public static ObjectMapper getObjectMapper() {
        if (objectMapper == null)
            objectMapper = new ObjectMapper();
        return objectMapper;
    }

    /**
     * bean转json
     *
     * @return
     */
    public static String beanToJson(Object o) {
        String json;
        try {
            json = getObjectMapper().writeValueAsString(o);
        } catch (IOException e) {
            e.printStackTrace();
            json = "";
        }
        return json;
    }

    /**
     * bean转json
     *
     * @return
     */
    public static String beanToFastJson(Object o) {
        String json = JSON.toJSONString(o);
        return json;
    }

    /**
     * bean转json
     *
     * @return
     */
    public static Object jsonToBean(String json, Class o) {
        Object result;
        try {
            result = objectMapper.readValue(json, o);
        } catch (IOException e) {
            e.printStackTrace();
            result = null;
        }
        return result;
    }

    /**
     * 将map装换为javabean对象
     *
     * @param map
     * @param bean
     * @return
     */
    public static <T> T mapToBean(Map<String, Object> map, T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

    /**
     * 将map装换为javabean对象
     *
     * @param bean
     * @return
     */
    public static Map beanToMap(Object bean) {
        BeanMap beanMap = BeanMap.create(bean);
        return beanMap;
    }

    /**
     * 从实体拷贝实体时 忽略current字段
     *
     * @param source
     * @param target
     */
    public static void copyProperties(Object source, Object target, String... list) {
        //成为瓶颈了再考虑
        String[] tempList = list == null ? ignoreList : list;
        if (source.getClass().equals(target.getClass())) {
            tempList = null;
        }
        BeanUtils.copyProperties(source, target, tempList);
    }


}
