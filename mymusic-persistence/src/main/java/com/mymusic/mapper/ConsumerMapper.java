package com.mymusic.mapper;

import com.mymusic.domain.Consumer;
import java.util.List;

public interface ConsumerMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Consumer record);

    int updateByPrimaryKey(Consumer record);

    List<Consumer> selectAll();
    
    Consumer selectByPrimaryKey(Integer id);

    Consumer selectByUserName(String username);

    int updateUserAvator(Consumer consumer);

}