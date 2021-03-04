package com.mymusic.service;

/*用户的逻辑处理*/
public interface AdminService {

    /*验证用户*/
    boolean verifyAdmin(String name,String password);
}
