package com.mymusic.mapper;

import com.mymusic.domain.ListSong;

import java.util.List;

/*歌单管理的mapper类的实现*/
public interface ListSongMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ListSong record);

    int insertSelective(ListSong record);

    ListSong selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ListSong record);

    int updateByPrimaryKey(ListSong record);

    int updateListSongMsg(ListSong record);

    int deleteListSong(Integer songId);

    /*根据歌单id和歌曲id的信息，来删除歌单中的记录*/
    int deleteBySongIdAndSongListId(Integer songId, Integer songListId);

    List<ListSong> allListSong();

    /*根据歌单中的id查询歌曲的的信息*/
    List<ListSong> listSongOfSongId(Integer songListId);

    /*根据歌曲的id查询表的信息*/
    ListSong selectBySongId(Integer songId);

}
