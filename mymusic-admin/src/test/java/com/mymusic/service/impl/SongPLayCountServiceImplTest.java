package com.mymusic.service.impl;

import com.mymusic.domain.SongPlayCount;
import com.mymusic.service.SongPlayCountService;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

public class SongPLayCountServiceImplTest extends BaseTest {

    @Resource
    private SongPlayCountService songPlayCountService;

    @Test
    public void addSongPlayCount() {
        SongPlayCount songPlayCount = new SongPlayCount();
        songPlayCount.setSongId(1L);
        songPlayCount.setUserId(1297873308628307970L);
        Boolean playCount = songPlayCountService.addSongPlayCount(songPlayCount);
        Assert.assertEquals(true, playCount);
    }

    @Test
    public void getSongPlayCountByUserId() {
        SongPlayCount songPlayCount = new SongPlayCount();
        songPlayCount.setUserId(1388878097171992578L);
        List<SongPlayCount> playCountByUserId = songPlayCountService.getPlayCountByUserId(1388878097171992578L);
        System.out.println(playCountByUserId.size());
    }

    @Test
    public void getTotalPlayCount() {
        Long totalPlayCount = songPlayCountService.getTotalPlayCount(1L);
        System.out.println(totalPlayCount);
    }

    @Test
    public void getTotalByUserId() {
        Integer total = songPlayCountService.getTotalByUserId(1388878097171992578L);
        System.out.println(total);
    }

    @Test
    public void getPlayCountByUserId() {
    }
}