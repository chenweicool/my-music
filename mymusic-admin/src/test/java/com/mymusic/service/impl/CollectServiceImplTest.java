package com.mymusic.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mymusic.common.domain.SongListCollectVo;
import com.mymusic.common.request.AddCollectSongListRequest;
import com.mymusic.domain.Collect;
import org.junit.Assert;
import org.junit.Test;
import org.omg.CORBA.CODESET_INCOMPATIBLE;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.Date;

import static org.junit.Assert.*;

public class CollectServiceImplTest extends BaseTest {

    @Resource
    private CollectServiceImpl collectService;

    @Test
    public void addCollectionSongList() {
        AddCollectSongListRequest collect = new AddCollectSongListRequest();
        collect.setUserId(1298090120930418690l);
        collect.setSongListId(14);
        boolean result = collectService.addCollectionSongList(collect);
        Assert.assertEquals(true, result);
    }

    @Test
    @Transactional
    public void deleteCollectBySongListId() {
        boolean result = collectService.deleteCollectBySongListId(3);
        Assert.assertEquals(true, result);
    }

    @Test
    public void getCollectsByUserId() {
        IPage<SongListCollectVo> collectsByUserId = collectService.getCollectsByUserId(1, 20, 1297873308628307970l);
        Assert.assertNotNull(collectsByUserId);
    }

    @Test
    public void getCount(){
        Integer count = collectService.getCount(14);
        System.out.println(count);
    }
}