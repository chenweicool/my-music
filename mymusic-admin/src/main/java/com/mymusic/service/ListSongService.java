package com.mymusic.service;

import com.mymusic.domain.ListSong;

import java.util.List;

/*歌单中的歌曲的管理*/
public interface ListSongService {

    /*增加歌单中的歌曲*/
    boolean addListSong(ListSong listSong);

    /*更新歌单中的歌曲*/
    boolean updateListSongMsg(ListSong listSong);

    /*删除*/
    boolean deleteListSong(Integer songId);

    /*查询所有的歌单的信息*/
    List<ListSong> allListSong();

    /*根据歌单的id查询歌单*/
    List<ListSong> listSongOfSongId(Integer songListId);

    /*根据歌曲的id和歌单的id来删除歌单中的信息*/
    boolean deleteBySongIdAndSongListId(Integer songId, Integer songListId);
}
