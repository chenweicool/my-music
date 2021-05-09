package com.mymusic.service.impl;

import com.mymusic.common.utils.ParameterCheckUtils;
import com.mymusic.domain.SongPlayCount;
import com.mymusic.mapper.SongPlayCountMapper;
import com.mymusic.service.SongPlayCountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SongPLayCountServiceImpl implements SongPlayCountService {

    @Resource
    private SongPlayCountMapper songPlayCountMapper;


    @Override
    public Boolean addSongPlayCount(SongPlayCount songPlayCount) {
        return null;
    }

    @Override
    public Long getTotalPlayCount(Long songId) {
        return null;
    }

    @Override
    public Integer getTotalByUserId(Long userId) {
        return null;
    }

    @Override
    public List<SongPlayCount> getPlayCountByUserId(Long userId) {
        return null;
    }
}
