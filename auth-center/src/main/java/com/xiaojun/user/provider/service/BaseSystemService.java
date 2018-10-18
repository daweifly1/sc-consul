package com.xiaojun.rbac.provider.service;

import com.xiaojun.db.service.BaseService;
import com.xiaojun.rbac.api.model.BaseSystem;
import com.xiaojun.rbac.api.pojo.response.ModuleAndSystemResponse;
import com.xiaojun.rbac.provider.mapper.BaseSystemMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fp295 on 2018/4/9.
 */
@Service
public class BaseSystemService extends BaseService<BaseSystem> {
    public List<ModuleAndSystemResponse> selectModuleAndSystem() {
        return ((BaseSystemMapper) mapper).selectModuleAndSystem();
    }
}
