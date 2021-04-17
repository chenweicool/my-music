package com.mymusic.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mymusic.domain.Singer;

import java.util.List;

public interface SingerMapper {

    /**
     * 增加歌手
     * @param singer 歌手
     * @return 插入结果
     */
    int insert(Singer singer);

    /**
     * 修改 先不使用
     */
    int updateSingerMsg(Singer singer);

    /*
    * 更新歌手的信息
    * */
    int updateByPrimaryKey(Singer singer);

    /**
     * 删除
     */
    int delete(Integer id);

    /**
     * 查询单个歌手
     */
    Singer findByPrimaryKey(Integer id);


    /**
     * 根据歌手名进行模糊查询
     * @param name 歌手名
     * @return 返回查询的结果
     */
    IPage<Singer> singerOfName(IPage<Singer> page,String name);

    /*根据歌手的性别来进行模糊查询*/
    List<Singer>  singerOfSex(Integer sex);

    /*插入歌手和歌曲的关联表*/
    int addSingerIdSongId(Integer singerId, Long songId);
}
