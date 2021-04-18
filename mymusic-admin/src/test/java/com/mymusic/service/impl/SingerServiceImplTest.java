package com.mymusic.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mymusic.domain.Singer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * 歌曲的测试信息
 */
@Slf4j
public class SingerServiceImplTest extends BaseTest {

    @Resource
    private SingerServiceImpl singerService;

    @Test
    public void findSinger() {
        Singer singer = singerService.findSinger(13);
        System.out.println(singer.toString());
    }

    /**
     * 测试性别通过
     */
    @Test
    public void singerOfSex() {
        IPage<Singer> iPage = singerService.singerOfSex(1, 20, 1);
        System.out.println(iPage.getTotal());
        Assert.assertNotNull(iPage.getRecords());
    }

    /*测试通过*/
    @Test
    public void singerOfName() {
        IPage<Singer> iPage = singerService.singerOfName(1, 20, "张");
        System.out.println(iPage.getTotal());
    }

    /*测试通过*/
    @Test
    public void getSingerByLocation() {
        IPage<Singer> singerByLocation = singerService.getSingerByLocation(1, 20, "中国台湾");
        System.out.println(singerByLocation.getTotal());

    }

    /*测试通过*/
    @Test
    public void getSingerByAge() {
        IPage<Singer> singerByAge = singerService.getSingerByAge(1, 20, 80);
        System.out.println(singerByAge.getTotal());
    }
}