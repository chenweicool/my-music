package com.mymusic.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mymusic.common.domain.SongVo;
import com.mymusic.mapper.SingerMapper;
import com.mymusic.domain.Singer;
import com.mymusic.common.enums.SingerConsumerType;
import com.mymusic.common.exception.SingerException;
import com.mymusic.service.SingerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SingerServiceImpl implements SingerService{

    @Resource
    private SingerMapper singerMapper;

    @Override
    public boolean addSinger(Singer singer) {
        return singerMapper.insert(singer)>0;
    }

    @Override
    public boolean updateSinger(Singer singer) {

        // 查询该用户是否存在
        Singer singerDb = singerMapper.findByPrimaryKey(singer.getId());
        if (singerDb == null) {
            throw new SingerException(SingerConsumerType.SINGER_NOT_EXIST);
        }
        int result = singerMapper.updateSingerMsg(singer);
        return result > 0;
    }

    @Override
    public boolean deleteSinger(Integer id) {
        Singer singer = singerMapper.findByPrimaryKey(id);
        if (singer == null) {
            throw new SingerException(SingerConsumerType.SINGER_NOT_EXIST);
        }
        return singerMapper.delete(id)>0;
    }

    @Override
    public Singer findSinger(Integer id) {
        return singerMapper.findByPrimaryKey(id);
    }


    @Override
    public IPage<Singer> singerOfName(Integer pageNum, Integer pageSize, String name) {
        IPage<Singer> page = new Page<>(pageNum, pageSize);
        return singerMapper.singerOfName(page,name);
    }

    @Override
    public List<Singer> singerOfSex(Integer sex) {
        return singerMapper.singerOfSex(sex);
    }

    @Override
    public boolean addSingerIdSongId(Integer singerId, Long songId) {

        return singerMapper.addSingerIdSongId(singerId,songId) > 0;
    }

}
