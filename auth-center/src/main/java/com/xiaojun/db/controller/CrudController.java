package com.xiaojun.db.controller;

import com.github.pagehelper.PageInfo;
import com.xiaojun.common.pojo.ResponseData;
import com.xiaojun.common.pojo.TableData;

import java.util.List;

/**
 * 单表维护 Controller 模板
 */
abstract public class CrudController<T, R> {

    /**
     * 表格查询
     */
    abstract protected ResponseData<TableData<T>> queryRecord(R query);

    /**
     * 添加记录
     */
    abstract protected ResponseData<T> addRecord(T record);

    /**
     * 批量删除记录
     */
    abstract protected ResponseData<T> deleteRecord(List<T> record);

    /**
     * 更新记录
     */
    abstract protected ResponseData<T> updateRecord(T record);

    /**
     * 返回表格数据
     */
    protected ResponseData<TableData<T>> getTableData(Integer code, String message, PageInfo<T> pageInfo) {
        TableData<T> data = new TableData();
        data.setTotal(pageInfo.getTotal());
        data.setRows(pageInfo.getList());
        return new ResponseData(code, message, data);
    }
}