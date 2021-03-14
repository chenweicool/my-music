package com.mymusic.mapper;

import com.mymusic.domain.Collect;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*用户收藏表mapper层的实现*/
public interface CollectMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Collect record);

    int insertSelective(Collect record);

    Collect selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Collect record);

    int updateByPrimaryKey(Collect record);

    int existSongId(@Param("userId") Integer userId, @Param("songId") Integer songId);

    int updateCollectMsg(Collect collect);

    int deleteCollect(@Param("userId") Integer userId, @Param("songId") Integer songId);

    List<Collect> allCollect();

    /*根据用户名返回他的收藏歌单信息*/
    List<Collect> collectionOfUser(Integer userId);

}
