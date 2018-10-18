package com.xiaojun.rbac.beans.dt;


import lombok.Data;

import java.io.Serializable;

@Data
public class SysRolePermission implements Serializable {

    private static final long serialVersionUID = 806991306525705549L;
    private Integer id;
    private Integer roleId;
    private Integer permissionId;

    // ######################## Constructor&ToString ########################
    public SysRolePermission() {
    }

    public SysRolePermission(Integer id, Integer roleId, Integer permissionId) {
        this.id = id;
        this.roleId = roleId;
        this.permissionId = permissionId;
    }
}