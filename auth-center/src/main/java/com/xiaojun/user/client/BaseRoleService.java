package com.xiaojun.rbac.client;

import com.xiaojun.auth.api.pojo.ResponseCode;
import com.xiaojun.common.pojo.ResponseData;
import com.xiaojun.rbac.api.InterfaceService;
import com.xiaojun.rbac.api.model.BaseRole;
import com.xiaojun.rbac.api.service.BaseRoleRemoteService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by fp295 on 2018/4/17.
 */
@FeignClient(name = InterfaceService.SERVICE_NAME, fallback = BaseRoleService.HystrixClientFallback.class)
public interface BaseRoleService extends BaseRoleRemoteService {

    class HystrixClientFallback implements BaseRoleService {

        @Override
        public ResponseData<List<BaseRole>> getRoleByUserId(@PathVariable("userId") String userId) {
            return new ResponseData<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
    }
}
