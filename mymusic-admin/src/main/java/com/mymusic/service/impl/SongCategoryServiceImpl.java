package com.mymusic.service.impl;

import com.mymusic.domain.SongCategory;
import com.mymusic.mapper.SongCategoryMapper;
import com.mymusic.service.SongCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SongCategoryServiceImpl implements SongCategoryService {

    @Resource
    private SongCategoryMapper categoryMapper;

    @Override
    public boolean insert(SongCategory songCategory) {
        return categoryMapper.insert(songCategory) > 0;
    }

    @Override
    public boolean delete(Integer id) {
        return categoryMapper.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public boolean update(SongCategory songCategory) {
        return categoryMapper.updateByPrimaryKey(songCategory) > 0;
    }

    @Override
    public List<SongCategory> selectAll() {
        return  categoryMapper.selectAll();
    }

    @Override
    public SongCategory selectById(Integer id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

}
