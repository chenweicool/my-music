package com.mymusic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mymusic.common.domain.SongVo;
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

     /*更新歌手的信息*/
    int updateByPrimaryKey(Song record);

    /*更新歌手的图片*/
    int  updateSongPic(Song song);

    /*分页查询歌曲*/
    IPage<SongVo> selectSongByPage(Page<Song> page);

    /*根据歌曲的url查询歌曲的id信息*/
    Long selectSongByUrl(String songUrl);

    //todo 这里有些冗余
    /*根据歌曲名返回歌曲信息*/
    List<Song> songName(@Param("songName") String songName);

    /*根据歌曲的查询参数返回查询的参数*/
    IPage<SongVo> querySongBySongNameOrSingerName(IPage<SongVo> page, @Param("queryName") String queryName);


    /*根据歌单的id返回歌曲的信息*/
    IPage<SongVo> selectSongBySongListId(IPage<SongVo> page,@Param("songListId") Integer songListId);

    /*根据歌手的id查询所有的歌曲*/
    IPage<SongVo> selectSongBySingerId(IPage<SongVo> page, @Param("singerId") Integer singerId);
}