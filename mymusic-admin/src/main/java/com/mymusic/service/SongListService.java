package com.mymusic.service;

import com.mymusic.domain.SongList;

import java.util.List;

/*歌单的处理逻辑的实现*/
public interface SongListService {

    /*增加一个歌单*/
    boolean addSongList (SongList songList);

    /*更新一个歌单的信息*/
    boolean updateSongListMsg(SongList songList);

    /*更新歌单的图片信息*/
    boolean updateSongListImg(SongList songList);

    /*根据id删除歌单的信息*/
    boolean deleteSongList(Integer id);

    /*查询所有的歌单信息*/
    List<SongList> allSongList();

    /*根据歌单的标模糊查询歌单信息*/
    List<SongList> likeTitle(String title);

    /*根据歌单的风格来查询歌单的信息*/
    List<SongList> likeStyle(String style);

    /*精确的查找歌单的信息*/
    List<SongList> songListOfTitle(String title);

    /*根据歌单的id查询单个歌单的信息*/
    SongList songListById(Integer parseInt);
}
