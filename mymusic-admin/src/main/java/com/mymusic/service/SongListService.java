package com.mymusic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mymusic.domain.SongList;

import java.util.List;

/*歌单的处理逻辑的实现*/
public interface SongListService {

    /*增加一个歌单*/
    boolean addSongList (SongList songList,Long userId);

    /*只有管理员才能增加的歌单信息*/
    boolean addSongList(SongList songList);

    /*更新一个歌单的信息*/
    boolean updateSongListMsg(SongList songList);

    /*更新歌单的图片信息*/
    boolean updateSongListImg(SongList songList);

    /*根据id删除歌单的信息*/
    boolean deleteSongList(Integer id);

    /*根据歌单的风格来查询歌单的信息*/
    IPage<SongList> likeStyle(Integer pageNum, Integer pageSize,String style);

    /*精确的查找歌单的信息*/
    IPage<SongList> songListOfTitle(Integer pageNum, Integer pageSize,String title);

    /*查询我的歌单信息*/
    IPage<SongList> findMySongList(Integer pageNum, Integer pageSize,Long userId);

    /*根据歌单的id查询单个歌单的信息*/
    SongList songListById(Integer parseInt);

    /*分页查询歌单信息*/
    IPage<SongList> getSongListByPage(Integer pageNum, Integer pageSize);
}
