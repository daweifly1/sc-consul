package com.xiaojun.rbac.provider.controller;

import com.github.pagehelper.PageInfo;
import com.xiaojun.auth.api.pojo.ResponseCode;
import com.xiaojun.auth.utils.AccessTokenUtils;
import com.xiaojun.common.pojo.ResponseData;
import com.xiaojun.common.pojo.TableData;
import com.xiaojun.common.utils.UUID;
import com.xiaojun.db.controller.CrudController;
import com.xiaojun.rbac.api.model.BaseModuleResources;
import com.xiaojun.rbac.api.model.BaseRoleModule;
import com.xiaojun.rbac.api.pojo.request.BaseModuleResourcesRequest;
import com.xiaojun.rbac.api.service.BaseModuleResourcesRemoteService;
import com.xiaojun.rbac.provider.service.BaseModuleResourceService;
import com.xiaojun.rbac.provider.service.BaseRoleModuleService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * Created by fp295 on 2018/4/17.
 */
@RestController
public class BaseModuleResourceController extends CrudController<BaseModuleResources, BaseModuleResourcesRequest> implements BaseModuleResourcesRemoteService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BaseModuleResourceService baseModuleResourceService;

    @Autowired
    private AccessTokenUtils accessTokenUtils;

    @Autowired
    private BaseRoleModuleService baseRoleModuleService;

    @Override
    public ResponseData<List<BaseModuleResources>> getMenusByUserId(@PathVariable("userId") String userId) {
        logger.debug("根据用户查询菜单");
        List<BaseModuleResources> list;
        try {
            list = baseModuleResourceService.getMenusByUserId(userId);
        } catch (Exception e) {
            logger.error("根据用户查询菜单错误");
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), list);
    }

    @GetMapping("/menu")
    public ResponseData<List<BaseModuleResources>> getCurrentMenu() {
        logger.debug("查询当前用户菜单");
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), accessTokenUtils.getMenuInfo());
    }

    @PostMapping(value = "/module/tree")
    private ResponseData<List<BaseModuleResources>> getModuleTree(@RequestBody BaseModuleResources moduleResources) {
        logger.debug("查询模块树");
        List<BaseModuleResources> list;
        try {
            list = baseModuleResourceService.getModuleTree(moduleResources.getId(), moduleResources.getSystemId());
        } catch (Exception e) {
            logger.error("查询模块树异常" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), list);
    }

    @PostMapping("/module/table")
    @Override
    protected ResponseData<TableData<BaseModuleResources>> queryRecord(@RequestBody BaseModuleResourcesRequest query) {
        logger.debug("查询模块表格");
        Example example = new Example(BaseModuleResources.class);
        Example.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(query.getParentId())) {
            criteria.andEqualTo("parentId", query.getParentId());
        } else if (!StringUtils.isEmpty(query.getSystemId())) {
            criteria.andEqualTo("systemId", query.getSystemId());
            criteria.andIsNull("parentId");
        } else {
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        example.orderBy("sort");
        PageInfo<BaseModuleResources> pageInfo = baseModuleResourceService.selectByExampleList(example, query.getPageNum(), query.getPageSize());

        return getTableData(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), pageInfo);
    }

    @PostMapping("/module")
    @Override
    protected ResponseData<BaseModuleResources> addRecord(@RequestBody BaseModuleResources record) {
        logger.debug("添加模块");
        try {
            record.setId(UUID.uuid32());
            record.setCreateDate(new Date());
            baseModuleResourceService.insertSelective(record);
        } catch (Exception e) {
            logger.error("添加模块失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    @DeleteMapping("/module")
    @Override
    protected ResponseData<BaseModuleResources> deleteRecord(@RequestBody List<BaseModuleResources> record) {
        logger.debug("删除模块");
        try {
            baseModuleResourceService.deleteBatchByPrimaryKey(record);
        } catch (Exception e) {
            logger.error("删除模块失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    @PutMapping("/module")
    @Override
    protected ResponseData<BaseModuleResources> updateRecord(@RequestBody BaseModuleResources record) {
        logger.debug("更新模块");
        try {
            record.setUpdateDate(new Date());
            baseModuleResourceService.updateByPrimaryKeySelective(record);
        } catch (Exception e) {
            logger.error("更新模块失败：" + e.getMessage());
            e.printStackTrace();
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
        return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }

    @GetMapping("/module/validate/{moduleCode}")
    public ResponseData<BaseModuleResources> validateModuleCode(@PathVariable("moduleCode") String moduleCode) {
        logger.debug("校验模块编码是否存在");
        BaseModuleResources baseModuleResources = new BaseModuleResources();
        baseModuleResources.setModuleCode(moduleCode);
        baseModuleResources = baseModuleResourceService.selectOne(baseModuleResources);
        if (baseModuleResources == null) {
            return new ResponseData<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
        }
        return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
    }

    @PostMapping("/module/role")
    public ResponseData saveRoleResourcesAuth(@RequestBody List<BaseRoleModule> roleModule) {
        logger.debug("保存角色权限");
        try {
            baseRoleModuleService.saveRoleModule(roleModule);
        } catch (RuntimeException e) {
            logger.error("保存角色权限失败" + e.getMessage());
            e.printStackTrace();
            return new ResponseData(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }

        return new ResponseData(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage());
    }
}
