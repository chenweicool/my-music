package com.mymusic.mapper;;

import com.mymusic.domain.SongCategory;
import java.util.List;

public interface SongCategoryMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SongCategory record);

    SongCategory selectByPrimaryKey(Integer id);

    List<SongCategory> selectAll();

    int updateByPrimaryKey(SongCategory record);
}