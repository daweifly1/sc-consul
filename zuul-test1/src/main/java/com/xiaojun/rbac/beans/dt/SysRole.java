package com.xiaojun.rbac.beans.dt;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysRole implements Serializable {

    private static final long serialVersionUID = 3199079341038842945L;
    private Integer id;
    private String name;
    private String remarks;
    private Integer createUserId;
    private Date createTime;
    // 它表数据
    private String reallyName;
    // 构建数据
    private Boolean isSelf = false;

    // ######################## Constructor&ToString ########################
    public SysRole() {
    }

    public SysRole(Integer id, String name, String remarks, Integer createUserId, Date createTime) {
        this.id = id;
        this.name = name;
        this.remarks = remarks;
        this.createUserId = createUserId;
        this.createTime = createTime;
    }
}