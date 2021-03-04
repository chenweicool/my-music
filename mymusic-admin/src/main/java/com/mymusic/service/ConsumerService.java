package com.mymusic.service;

import com.mymusic.domain.Consumer;
import java.util.List;

public interface ConsumerService {

    /*增加一个用户*/
    boolean addUser(Consumer consumer);

    /*更新用户的信息*/
    boolean updateUserMsg(Consumer consumer);

    /*更新用户的头像*/
    boolean updateUserAvator(Consumer consumer);

    /*用户是否存在*/
    boolean existUser(String username);

    /*验证用户的信息*/
    boolean veritypasswd(String username, String password);

    /*根据用户的id删除用户*/
    boolean deleteUser(Integer id);

    /*查询所有的用户*/
    List<Consumer> allUser();

    /*根据用户id查询用户信息*/
    Consumer userOfId(Integer id);

    /*用户的登陆状态*/
    Consumer loginStatus(String username);
}
