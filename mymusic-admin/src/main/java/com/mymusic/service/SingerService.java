package com.mymusic.service;

import com.mymusic.domain.Singer;

import java.util.List;

/**
 * 歌手的信息处理类
 */
public interface SingerService {
    /**
     * 增加歌手
     * @param singer 歌手
     * @return 插入结果
     */
    boolean addSinger(Singer singer);

    /*
     * 更新歌手的信息
     * */
    boolean updateSinger(Singer singer);

    /**
     * 删除
     */
    boolean deleteSinger(Integer id);

    /**
     * 查询单个歌手
     */
    Singer findSinger(Integer id);

    /**
     * 查询全部的歌手
     */
    List<Singer> findAllSinger();

    /**
     * 根据歌手名进行模糊查询
     * @param name 歌手名
     * @return 返回查询的结果
     */
    List<Singer> singerOfName(String name);

    /*根据歌手的性别来进行模糊查询*/
    List<Singer>  singerOfSex(Integer sex);

    /*将歌手的id和歌曲的id插入到关联表中去*/
    boolean addSingerIdSongId(Integer singer_id, Long song_id);

}

