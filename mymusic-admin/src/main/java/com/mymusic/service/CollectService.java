package com.mymusic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mymusic.common.domain.SongListCollectVo;
import com.mymusic.common.domain.SongVo;
import com.mymusic.common.exception.AjaxResponse;
import com.mymusic.common.request.AddCollectRequest;

/*
* 用户的歌单收集类的实现
* */
public interface CollectService {

    /**
     *用户添加收藏实现
     * 根据标志位来区分收藏的类型
     * @param collect {@link com.mymusic.domain.Collect}
     * @return
     */
    AjaxResponse addCollection(AddCollectRequest collect);

    /*取消收藏的实现*/
    boolean deleteCollectBySongListId(Integer collectSongListId,Long userId);

    /*查询用户的收藏歌单信息*/
    AjaxResponse getCollectsByUserId(Integer pageNum, Integer pageSize, Long userIdDB, Integer type);

    /*返回歌单被收藏的数量*/
    Integer getCount(Integer songListCollectID);
}
