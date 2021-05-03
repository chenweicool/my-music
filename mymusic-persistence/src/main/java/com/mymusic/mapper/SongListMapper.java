package com.mymusic.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mymusic.domain.SongList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*用户的歌单列表dao层*/
public interface SongListMapper {

    /*删除一个歌单*/
    int deleteByPrimaryKey(Integer id);

    /*增加一个歌单*/
    int insert(SongList record);

   /*根据主键查询歌单*/
    SongList selectByPrimaryKey(Integer id);


    /*更新歌单的信息*/
    int updateByPrimaryKey(SongList record);

    /*更新歌单的图片*/
    int  updateSongListImg(SongList songList);

    /*根据uuid返回用户的信息*/
    SongList findSongListByUUID(String uuid);

    /*根据歌曲的风格返回应有歌曲的信息*/
    IPage<SongList> likeStyle(IPage<SongList> page,@Param("style") String style);

    /*精确查找歌单的标题(一般要求歌单的标题唯一)*/
    IPage<SongList> songListOfTitle(IPage<SongList> page,@Param("title") String title);

    /*根据用户的id查询用户的创建的歌单信息*/
    IPage<SongList> findMySongList(IPage<SongList> page,@Param("userId") Long userId);

    /*分页查询歌单信息*/
    IPage<SongList> getSongListByPage(IPage<SongList> page);

    /*获取10个歌单信息*/
    List<SongList> getSongListHot();

}