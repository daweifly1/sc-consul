package com.xiaojun.rbac.api.service;

import com.xiaojun.common.pojo.ResponseData;
import com.xiaojun.rbac.api.model.BaseModuleResources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by fp295 on 2018/4/16.
 */
public interface BaseModuleResourcesRemoteService {

    /**
     * 根据userId查询菜单
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/menu/user/{userId}", method = RequestMethod.GET)
    ResponseData<List<BaseModuleResources>> getMenusByUserId(@PathVariable("userId") String userId);
}
