package com.mymusic.mapper;

import com.mymusic.domain.Admin;


public interface AdminMapper {

    /*根据用户名查询用户*/
    Admin findByUsername(String name);
}
