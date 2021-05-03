package com.mymusic.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mymusic.common.domain.SongListCollectVo;
import com.mymusic.common.exception.AjaxResponse;
import com.mymusic.common.request.AddCollectRequest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

public class CollectServiceImplTest extends BaseTest {

    @Resource
    private CollectServiceImpl collectService;

    @Test
    public void addCollectionSongList() {
        AddCollectRequest collect = new AddCollectRequest();
        collect.setUserId(1298090120930418690l);
        collect.setSongId(1l);
        AjaxResponse result = collectService.addCollection(collect);
        System.out.println(result.getMessage());
        //Assert.assertEquals(true, result);
    }

    @Test
    @Transactional
    public void deleteCollectBySongListId() {
        boolean result = collectService.deleteCollectBySongListId(3,1298090120930418690L);
        Assert.assertEquals(true, result);
    }

    @Test
    public void getCollectsByUserId() {
        AjaxResponse response = collectService.getCollectsByUserId(1, 20, 1298090120930418690L,1);
        System.out.println(response.getData());
        Assert.assertNotNull(response);

    }

    @Test
    public void getCount(){
        Integer count = collectService.getCount(14);
        System.out.println(count);
    }
}