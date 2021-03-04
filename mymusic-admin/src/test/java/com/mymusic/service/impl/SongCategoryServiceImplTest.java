package com.mymusic.service.impl;

import com.mymusic.domain.SongCategory;
import com.mymusic.service.SongCategoryService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * 测试成功
 */
public class SongCategoryServiceImplTest extends BaseTest {

    @Resource
    private SongCategoryService songCategoryService;

    @Test
    @Transactional
    public void insert() {
        SongCategory songCategory = new SongCategory();
        songCategory.setCategoryName("国语");
        boolean flag = songCategoryService.insert(songCategory);
        Assert.assertEquals(true, flag);
    }

    @Test
    @Transactional
    public void delete() {
        boolean flag = songCategoryService.delete(1);
        Assert.assertEquals(true, flag);
    }

    @Test
    @Transactional
    public void update() {
        SongCategory songCategory = songCategoryService.selectById(1);
        songCategory.setCategoryName("轻音乐");
        boolean flag = songCategoryService.update(songCategory);
        Assert.assertEquals(true, flag);
    }

    @Test
    public void selectAll() {
        List<SongCategory> songCategoryList = songCategoryService.selectAll();
        Assert.assertNotNull(songCategoryList);

    }

    @Test
    public void selectById(){
    }
}