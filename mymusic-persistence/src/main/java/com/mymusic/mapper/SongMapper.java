package com.mymusic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mymusic.domain.Song;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 歌曲的管理类的接口的实现
 */
public interface SongMapper extends BaseMapper<Song> {

    /*删除歌手的信息*/
    int deleteByPrimaryKey(Long  id);

    /*插入歌手的信息*/
    int insert(Song record);

    /*根据歌手的id进行查询*/
    Song selectByPrimaryKey(Long id);

   /*查询所有的歌手*/
    List<Song> selectAll();

     /*更新歌手的信息*/
    int updateByPrimaryKey(Song record);

    /*根据歌曲的名字，查询歌曲的信息*/
    List<Song> songOfName(String name);

    /*更新歌手的图片*/
    int  updateSongPic(Song song);

    /*根据歌手的id查询所有的歌曲*/
    List<Song> selectSongBySingerId(Integer singerId);

    /*分页查询歌曲*/
    IPage<Song> selectSong(Page<Song> page,
                           @Param("name") String name,
                           @Param("introduction") String introduction,
                           @Param("lyric") String lyric);

    /*根据歌曲的url查询歌曲的id信息*/
    Long selectSongByUrl(String songUrl);

    /*根据歌单的id返回歌曲的信息*/
    List<Song> selectSongBySongListId(Integer songListId);
}