package com.xiaojun.rbac.provider.mapper;


import com.xiaojun.rbac.api.model.BaseRoleModule;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface BaseRoleModuleMapper extends Mapper<BaseRoleModule> {
    List<BaseRoleModule> selectLeafRoleModule(String roleId);
}