package com.xiaojun.rbac.beans.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author: Mr.X
 * Date: 2018/1/2 下午2:36
 * Description:
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TreeNode {
    private Integer id;
    private Integer parentId;
    private String name;
    private Boolean checked = false;
}
