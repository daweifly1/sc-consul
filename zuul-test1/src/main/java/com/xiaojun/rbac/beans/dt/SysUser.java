package com.xiaojun.rbac.beans.dt;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class SysUser implements Serializable {

    private static final long serialVersionUID = -5801248243857686952L;
    private Integer id;
    private String username;
    private String password;
    private String reallyName;
    private String email;
    private String mobile;
    private Integer status;
    private Date createTime;
    private Date updateTime;
    private String loginIp;
    private Integer loginTimes;

    // 它表数据
    private List<SysRole> sysRoleList;

    // ######################## Constructor&ToString ########################
    public SysUser() {
    }

    public SysUser(Integer id, String username, String password, String reallyName, String email, String mobile, Integer status, Date createTime, Date updateTime, String loginIp, Integer loginTimes) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.reallyName = reallyName;
        this.email = email;
        this.mobile = mobile;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.loginIp = loginIp;
        this.loginTimes = loginTimes;
    }

}