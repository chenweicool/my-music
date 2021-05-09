package com.mymusic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mymusic.common.utils.ParameterCheckUtils;
import com.mymusic.domain.SongPlayCount;
import com.mymusic.mapper.SongPlayCountMapper;
import com.mymusic.service.SongPlayCountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SongPLayCountServiceImpl implements SongPlayCountService {

    @Resource
    private SongPlayCountMapper songPlayCountMapper;

    // todo  这里需要添加缓存 redis来实现
    @Override
    public Boolean addSongPlayCount(SongPlayCount songPlayCount) {
        ParameterCheckUtils.checkParamIsBlank(songPlayCount);
        ParameterCheckUtils.checkParamIsBlank(songPlayCount.getSongId(),songPlayCount.getUserId());
        // 这里还需要查询一下
        SongPlayCount playCountByUserId = getSongPlayCountByUserId(songPlayCount.getUserId(), songPlayCount.getSongId());
        if (playCountByUserId != null) {
            Integer playCount = playCountByUserId.getPlayCount();
            playCountByUserId.setUpdateTime(new Date());
            playCountByUserId.setPlayCount(playCount + 1);  // 播放次数+1
            return  songPlayCountMapper.updateById(playCountByUserId) > 0;
        }else{
            songPlayCount.setCreateTime(new Date());
            songPlayCount.setUpdateTime(new Date());
            songPlayCount.setPlayCount(1);
           return songPlayCountMapper.insert(songPlayCount) > 0;
        }
    }

    /**
     * 根据userId和songId 来查询一条记录
     * 不能用官方的方法，有坑
     * @param userId 用户的id
     * @param songId   歌曲的Id
     * @return {@link SongPlayCount}
     */
    @Override
    public SongPlayCount getSongPlayCountByUserId(Long userId, Long songId) {
        return songPlayCountMapper.getSongPlayCountByUserId(userId,songId);
    }

    /**
     * 实现思路： 根据songId查询出所有的用户单个播放记录，然后相加
     * 如果有几十个亿的用户，那就很恼火，这儿的遍历就会出现问题
     * todo 待优化
     * @param songId 歌曲id的信息
     * @return
     */
    @Override
    public Long getTotalPlayCount(Long songId) {
        List<SongPlayCount> songList = songPlayCountMapper.getSongPlayById(songId);
         Long totalPlayCount = 0L;
        for (SongPlayCount songPlayCount : songList) {
              totalPlayCount += songPlayCount.getPlayCount();
        }
        return totalPlayCount;
    }


    /**
     * 返回用户的听歌总数
     * @param userId 用户id
     * @return
     */
    @Override
    public Integer getTotalByUserId(Long userId) {
       Integer result =  songPlayCountMapper.getTotalPlayByUserId(userId);
        return result;
    }

    /**
     * 查询一个用户听歌总数量
     * @param userId 用户id
     * @return
     */
    @Override
    public List<SongPlayCount> getPlayCountByUserId(Long userId) {
        List playCountList = songPlayCountMapper.getPlaySongByUserId(userId);
        return playCountList;
    }
}
