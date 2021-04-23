package com.mymusic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mymusic.common.domain.SongListCollectVo;
import com.mymusic.common.request.AddCollectSongListRequest;
import com.mymusic.domain.Collect;

import java.util.List;

/*
* 用户的歌单收集类的实现
* */
public interface CollectService {

    /*添加收藏*/
    boolean addCollectionSongList(AddCollectSongListRequest collect);

    /*取消收藏的实现*/
    boolean deleteCollectBySongListId(Integer collectSongListId);

    /*查询用户的收藏歌单信息*/
    IPage<SongListCollectVo> getCollectsByUserId(Integer pageNum, Integer pageSize, Long userIdDB);

    /*返回歌单被收藏的数量*/
    Integer getCount(Integer songListCollectID);
}
