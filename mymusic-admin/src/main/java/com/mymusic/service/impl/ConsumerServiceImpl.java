package com.mymusic.service.impl;

import com.mymusic.domain.Consumer;
import com.mymusic.common.enums.UserConsumerType;
import com.mymusic.common.exception.UserException;
import com.mymusic.mapper.ConsumerMapper;
import com.mymusic.service.ConsumerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户的处理信息类
 */
@Service
public class ConsumerServiceImpl implements ConsumerService {

    @Resource
    private ConsumerMapper consumerMapper;

    @Override
    public boolean addUser(Consumer consumer) {
        return consumerMapper.insert(consumer) > 0;
    }

    @Override
    public boolean updateUserMsg(Consumer consumer) {
        Consumer consumer1 = consumerMapper.selectByPrimaryKey(consumer.getId());
        if (consumer1 == null) {
            throw new UserException(UserConsumerType.USER_NOT_EXIST.getMessage());
        }
        return consumerMapper.updateByPrimaryKey(consumer) > 0;
    }

    @Override
    public boolean updateUserAvator(Consumer consumer) {
        Consumer consumer1 = consumerMapper.selectByPrimaryKey(consumer.getId());
        if (consumer1 == null) {
            throw new UserException(UserConsumerType.USER_NOT_EXIST.getMessage());
        }
        return consumerMapper.updateUserAvator(consumer) > 0;
    }

    /*验证用户登陆信息*/
    @Override
    public boolean veritypasswd(String username, String password) {
        Consumer consumer = consumerMapper.selectByUserName(username);
        if (consumer == null) {
            throw new UserException(UserConsumerType.USER_NOT_EXIST.getMessage());
        }
        String passwordDb = consumer.getPassword();
        if (!password.equals(passwordDb)) {
            throw new UserException(UserConsumerType.PASSWORD_ERROR.getMessage());
        }
        return true;
    }

    @Override
    public boolean deleteUser(Integer id) {
        Consumer consumer = consumerMapper.selectByPrimaryKey(id);
        if (consumer == null) {
            throw new UserException(UserConsumerType.USER_NOT_EXIST.getMessage());
        }
        return consumerMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public List<Consumer> allUser() {
        return consumerMapper.selectAll();
    }

    @Override
    public Consumer userOfId(Integer id) {
        Consumer consumer = consumerMapper.selectByPrimaryKey(id);
        if (consumer == null) {
            throw new UserException(UserConsumerType.USER_NOT_EXIST.getMessage());
        }
        return consumer;
    }

    @Override
    public Consumer loginStatus(String username) {
        return consumerMapper.selectByUserName(username);
    }

    @Override
    public boolean existUser(String username) {
        return false;
    }
}
