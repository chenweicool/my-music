package com.mymusic.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mymusic.common.domain.SongListCollectVo;
import com.mymusic.common.domain.SongVo;
import com.mymusic.common.exception.AjaxResponse;
import com.mymusic.common.request.AddCollectRequest;
import com.mymusic.common.utils.ParameterCheckUtils;
import com.mymusic.domain.Collect;
import com.mymusic.mapper.CollectMapper;
import com.mymusic.service.CollectService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;

@Service
public class CollectServiceImpl implements CollectService {

    @Resource
    private CollectMapper collectMapper;


    @Override
    public AjaxResponse addCollection(AddCollectRequest request) {
        ParameterCheckUtils.checkParamIsBlank(request);
        ParameterCheckUtils.checkParamIsBlank(request.getUserId());

        Collect collect = new Collect();
        collect.setUserId(request.getUserId());
        // 根据type来判断收藏的类别信息
        if (request.getSongId() != null) {
            collect.setType(1);
            // 检查歌曲是否被收藏
            Collect collect1 = collectMapper.selectByUserIdSongId(request.getUserId(), request.getSongId());
            if (collect1 != null) {
                return AjaxResponse.success("已收藏，不能重复收藏");
            }
            collect.setSongId(request.getSongId());
        }else if(request.getSongListId() != null){
            collect.setType(0);
            Collect userSongListCollect= collectMapper.selectByUserSongListId(request.getUserId(), request.getSongListId());
            if (userSongListCollect != null) {
                return AjaxResponse.success("已收藏，不能重复收藏");
            }
            collect.setSongListId(request.getSongListId());
        }
        collect.setCreateTime(new Date());
        collect.setUpdateTime(new Date());
        int result = collectMapper.addCollectionSongList(collect);
        if (result > 0) {
            return AjaxResponse.success("收藏成功");
        }
        return  AjaxResponse.error("收藏失败");
    }

    @Override
    public boolean deleteCollectBySongListId(Integer collectSongListId,Long userId) {
        return collectMapper.deleteCollectBySongListId(collectSongListId,userId) > 0;
    }

    @Override
    public AjaxResponse getCollectsByUserId(Integer pageNum, Integer pageSize, Long userIdDB, Integer type) {
        ParameterCheckUtils.checkParamIsBlank(userIdDB,type);
        if(type == 0){
            IPage<SongListCollectVo> page = new Page<>(pageNum, pageSize);
            IPage<SongListCollectVo> collectVoIPage = collectMapper.getCollectsByUserId(page, userIdDB, type);
            return AjaxResponse.success(collectVoIPage);
        }else{
            IPage<SongVo> page = new Page<>(pageNum, pageSize);
            IPage<SongVo> collectsByUserSong = collectMapper.getCollectsByUserSong(page, userIdDB, type);
            return AjaxResponse.success(collectsByUserSong);
        }
    }


    @Override
    public Integer getCount(Integer songListCollectID) {
        Integer count = collectMapper.getCount(songListCollectID);
        return count;
    }
}
