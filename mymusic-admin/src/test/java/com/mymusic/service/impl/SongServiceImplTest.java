package com.mymusic.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mymusic.common.domain.SongVo;
import com.mymusic.domain.Song;
import com.mymusic.service.SongService;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

public class SongServiceImplTest extends BaseTest {

    @Resource
    private SongService songService;

    /**
     * 分页查询的测试
     */
    @Test
    public void querySong() {
        IPage<SongVo> page = songService.selectSongByPage( 1, 2);
        List<SongVo> songList = page.getRecords();
        for (SongVo song : songList) {
            System.out.println(song.toString());
        }
        System.out.println("查出的页的数据大小是："+songList.size());
        Assert.assertNotNull(page);
    }
}