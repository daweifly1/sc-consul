package com.xiaojun.rbac.beans.dt;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysPermission implements Serializable {

    private static final long serialVersionUID = -2952901891556881956L;
    private Integer id;
    private String name;
    private String url;
    private Integer type;
    private Integer parentId;
    private Date createTime;

    // ######################## Constructor&ToString ########################
    public SysPermission() {
    }

    public SysPermission(Integer id, String name, String url, Integer type, Integer parentId, Date createTime) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.type = type;
        this.parentId = parentId;
        this.createTime = createTime;
    }
}