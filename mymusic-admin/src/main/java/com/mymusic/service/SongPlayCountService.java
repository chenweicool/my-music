package com.mymusic.service;

import com.mymusic.domain.SongPlayCount;

import java.util.List;

public interface SongPlayCountService {

    /**
     * 新增一条播放记录
     * @param songPlayCount {@link SongPlayCount}
     * @return
     */
    Boolean addSongPlayCount(SongPlayCount songPlayCount);

    /**
     * 根据userId和播放的Id查询一条地址
     */
    SongPlayCount getSongPlayCountByUserId(Long userId, Long songId);

    /**
     * 一首歌曲总的播放次数
     * @param songId 歌曲id的信息
     * @return
     */
    Long getTotalPlayCount(Long songId);

    /**
     * 查询用户总的听歌数量，
     * @param userId 用户id
     * @return 听歌的数量
     */
    Integer getTotalByUserId(Long userId);

    /**
     * 返回用户总的听歌历史记录信息
     * 根据用户听歌次数来进行返回
     * @param userId 用户id
     * @return {@link SongPlayCount}
     */
    List<SongPlayCount> getPlayCountByUserId(Long userId);

}
