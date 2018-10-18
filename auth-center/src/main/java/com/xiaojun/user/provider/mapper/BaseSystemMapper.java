package com.xiaojun.rbac.provider.mapper;


import com.xiaojun.rbac.api.model.BaseSystem;
import com.xiaojun.rbac.api.pojo.response.ModuleAndSystemResponse;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface BaseSystemMapper extends Mapper<BaseSystem> {
    List<ModuleAndSystemResponse> selectModuleAndSystem();
}