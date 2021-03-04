package com.mymusic.service;

import com.mymusic.domain.SongCategory;

import java.util.List;

/**
 * 歌曲类别的接口
 */
public interface SongCategoryService {

    boolean insert(SongCategory songCategory);

    boolean delete(Integer id);

    boolean update(SongCategory songCategory);

    List<SongCategory> selectAll();

    SongCategory selectById(Integer id);
}
