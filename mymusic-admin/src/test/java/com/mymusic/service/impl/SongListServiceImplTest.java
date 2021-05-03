package com.mymusic.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mymusic.domain.Singer;
import com.mymusic.domain.SongList;
import com.mymusic.service.SongListService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

public class SongListServiceImplTest extends BaseTest {

    @Resource
    private SongListService songListService;
    @Test
    public void findMySongList() {
        IPage<SongList> mySongList = songListService.findMySongList(1,20,1297873308628307970l);
        Assert.assertNotNull(mySongList);
    }

    @Test
    @Transactional
    public void addSongList(){
        SongList songList = new SongList();
        songList.setPic("/url");
        songList.setIntroduction("这又是一个测试的歌单信息");
        songList.setTitle("忧郁的歌单信息");
        songList.setStyle("运动风格");
        songList.setType(1);
        Long userId = 1297873308628307970l;
        boolean result = songListService.addSongListUserId(songList, userId);
        Assert.assertEquals(true, result);
    }

    @Test
    @Transactional
    public void deleteSongList(){
        boolean flag = songListService.deleteSongList(9);
        Assert.assertEquals(true, flag);
    }

    @Test
    public void testSongListName(){
        IPage<SongList> page = songListService.likeStyle(1, 20, "华语");
        System.out.println(page.getTotal());
        Assert.assertNotNull(page.getRecords());
    }

    @Test
    public void testSongListTitle(){
        IPage<SongList> page = songListService.songListOfTitle(1, 20, "肾上腺素飙升");
        System.out.println(page.getTotal());
        Assert.assertNotNull(page.getRecords());
    }
}