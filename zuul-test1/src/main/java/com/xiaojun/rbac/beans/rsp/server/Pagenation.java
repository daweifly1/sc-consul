package com.xiaojun.rbac.beans.rsp.server;

import lombok.Data;

import java.util.List;

/**
 * Author: Mr.X
 * Date: 2017/12/24 下午2:49
 * Description:
 */
@Data
public class Pagenation<T> {

    private boolean success;
    private int curPage;
    private int pageSize;
    private long totalRows;
    private String sortName;
    private String sortOrder;
    private List<T> data;


    /**
     * @param success   是否成功
     * @param curPage   当前页
     * @param pageSize  每页显示数量
     * @param totalRows 总条数
     * @param sortName  排序名
     * @param sortOrder 排序规则
     * @param data      数据集合
     */
    public Pagenation(boolean success, int curPage, int pageSize, long totalRows, String sortName, String sortOrder, List<T> data) {
        this.success = success;
        this.curPage = curPage;
        this.pageSize = pageSize;
        this.totalRows = totalRows;
        this.sortName = sortName;
        this.sortOrder = sortOrder;
        this.data = data;
    }
}
