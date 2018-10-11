package com.xiaojun.user.provider.service;

import com.xiaojun.db.service.BaseService;
import com.xiaojun.user.api.model.BaseRole;
import com.xiaojun.user.provider.mapper.BaseRoleMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fp295 on 2018/4/9.
 */
@Service
public class BaseRoleService extends BaseService<BaseRole> {

    /**
     * 根据用户查询角色
     * @param userId
     * @return
     */
    public List<BaseRole> getRoleByUserId(String userId) {
        return ((BaseRoleMapper)mapper).getRoleByUserId(userId);
    }
}
