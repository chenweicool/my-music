package com.mymusic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mymusic.domain.SongPlayCount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SongPlayCountMapper extends BaseMapper<SongPlayCount> {

    /*根据用户返回一条记录*/
    SongPlayCount getSongPlayCountByUserId(@Param("userId") Long userId,@Param("songId") Long songId);

    /*返回今天播放的歌曲数量*/
    List<SongPlayCount> getSongPlayById(@Param("songId") Long songId);

    /*返回用户总的听歌数量*/
    Integer getTotalPlayByUserId(@Param("userId") Long userId);

    /*返回用户听歌历史记录详情*/
    List<SongPlayCount> getPlaySongByUserId(@Param("userId") Long userId);
}
