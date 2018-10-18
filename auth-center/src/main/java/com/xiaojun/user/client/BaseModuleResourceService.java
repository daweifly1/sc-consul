package com.xiaojun.rbac.client;

import com.xiaojun.auth.api.pojo.ResponseCode;
import com.xiaojun.common.pojo.ResponseData;
import com.xiaojun.rbac.api.InterfaceService;
import com.xiaojun.rbac.api.model.BaseModuleResources;
import com.xiaojun.rbac.api.service.BaseModuleResourcesRemoteService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


/**
 * Created by fp295 on 2018/4/17.
 */
@FeignClient(name = InterfaceService.SERVICE_NAME, fallback = BaseModuleResourceService.HystrixClientFallback.class)
public interface BaseModuleResourceService extends BaseModuleResourcesRemoteService {

    class HystrixClientFallback implements BaseModuleResourceService {

        @Override
        public ResponseData<List<BaseModuleResources>> getMenusByUserId(@PathVariable("userId") String userId) {
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
    }
}
