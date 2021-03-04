package com.mymusic.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mymusic.domain.Song;
import com.mymusic.service.SongService;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

public class SongServiceImplTest extends BaseTest {

    @Resource
    private SongService songService;

    @Test
    public void querySong() {
        IPage<Song> page = songService.querySong("", "", "", 1, 2);
        List<Song> songList = page.getRecords();
        for (Song song : songList) {
            System.out.println(song.toString());
        }
        System.out.println("查出的页的数据大小是："+songList.size());
        Assert.assertNotNull(page);
    }
}