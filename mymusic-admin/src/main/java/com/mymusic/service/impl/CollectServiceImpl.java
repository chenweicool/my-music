package com.mymusic.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mymusic.common.domain.SongListCollectVo;
import com.mymusic.common.request.AddCollectSongListRequest;
import com.mymusic.domain.Collect;
import com.mymusic.mapper.CollectMapper;
import com.mymusic.service.CollectService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class CollectServiceImpl implements CollectService {

    @Resource
    private CollectMapper collectMapper;

    @Override
    public boolean addCollectionSongList(AddCollectSongListRequest request) {
        Collect collect = new Collect();
        collect.setUserId(request.getUserId());
        collect.setType(0);
        collect.setSongListId(request.getSongListId());
        collect.setCreateTime(new Date());
        collect.setUpdateTime(new Date());
        return collectMapper.addCollectionSongList(collect) > 0;
    }

    @Override
    public boolean deleteCollectBySongListId(Integer collectSongListId) {
        return collectMapper.deleteCollectBySongListId(collectSongListId) > 0;
    }

    @Override
    public IPage<SongListCollectVo> getCollectsByUserId(Integer pageNum, Integer pageSize, Long userIdDB) {
        IPage<SongListCollectVo> page = new Page<>(pageNum, pageSize);
        return collectMapper.getCollectsByUserId(page,userIdDB);
    }

    @Override
    public Integer getCount(Integer songListCollectID) {
        Integer count = collectMapper.getCount(songListCollectID);
        return count;
    }
}
