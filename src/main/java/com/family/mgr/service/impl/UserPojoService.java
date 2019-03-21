package com.family.mgr.service.impl;

import com.family.config.StringUtil;
import com.family.mgr.mapper.UserPojoMapper;
import com.family.mgr.pojo.UserPojo;
import com.family.mgr.service.IUserPojoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: yuandh
 * @Description:
 * @Date: Created in 15:11 2019/3/21
 * @Modified By:
 */
@Service
public class UserPojoService implements IUserPojoService {

    @Autowired
    UserPojoMapper mapper;

    @Override
    public int insert(UserPojo userPojo) {
        userPojo.setId(StringUtil.getUUID());
        return mapper.insert(userPojo);
    }
}
