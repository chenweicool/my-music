package com.mymusic.service.impl;

import com.mymusic.mapper.AdminMapper;
import com.mymusic.domain.Admin;
import com.mymusic.common.enums.UserConsumerType;
import com.mymusic.common.exception.UserException;
import com.mymusic.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 管理员的实现类
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    @Override
    public boolean verifyAdmin(String name,String password) {
        Admin admin = adminMapper.findByUsername(name);
        if (admin == null) {
            throw new UserException(UserConsumerType.USER_NOT_EXIST);
        }

        String dbPassword = admin.getPassword();
        if(!dbPassword.equals(password)){
            throw new UserException(UserConsumerType.PASSWORD_ERROR);
        }
        return true;
    }
}
