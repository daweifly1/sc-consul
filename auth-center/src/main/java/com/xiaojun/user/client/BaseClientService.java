package com.xiaojun.user.client;

import com.xiaojun.auth.api.pojo.ResponseCode;
import com.xiaojun.common.pojo.ResponseData;
import com.xiaojun.user.api.InterfaceService;
import com.xiaojun.user.api.model.OauthClientDetails;
import com.xiaojun.user.api.service.BaseClientRemoteService;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

/**
 * Created by fp295 on 2018/4/9.
 */
@FeignClient(name = InterfaceService.SERVICE_NAME, fallback = BaseClientService.HystrixClientFallback.class)
public interface BaseClientService extends BaseClientRemoteService {

    class HystrixClientFallback implements BaseClientService {

        @Override
        public ResponseData<List<OauthClientDetails>> getAllClient() {
            return new ResponseData(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMessage());
        }
    }
}
