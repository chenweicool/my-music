package com.mymusic.mapper;

import com.mymusic.domain.SongList;
import java.util.List;

/*用户的歌单列表dao层*/
public interface SongListMapper {

    /*删除一个用户*/
    int deleteByPrimaryKey(Integer id);

    /*增加一个用户*/
    int insert(SongList record);


   /*根据主键查询歌单*/
    SongList selectByPrimaryKey(Integer id);

   /*查询所有的歌单的信息*/
    List<SongList> selectAll();

    /*更新歌单的信息*/
    int updateByPrimaryKey(SongList record);

    /*更新歌单的图片*/
    int  updateSongListImg(SongList songList);

    /*根据标题返回歌曲的信息*/
    List<SongList> likeTitle(String title);

    /*根据歌曲的风格返回应有歌曲的信息*/
    List<SongList> likeStyle(String style);

    /*精确查找歌单的标题*/
    List<SongList> songListOfTitle(String title);
}