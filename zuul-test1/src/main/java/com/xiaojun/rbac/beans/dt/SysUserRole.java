package com.xiaojun.rbac.beans.dt;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysUserRole implements Serializable {

    private static final long serialVersionUID = -4119577762633389888L;
    private Integer id;
    private Integer userId;
    private Integer roleId;

    // ######################## Constructor&ToString ########################
    public SysUserRole() {
    }

    public SysUserRole(Integer id, Integer userId, Integer roleId) {
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
    }

}