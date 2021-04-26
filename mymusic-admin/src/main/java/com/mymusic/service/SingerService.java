package com.mymusic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mymusic.domain.Singer;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 歌手的信息处理类
 */
public interface SingerService {
    /**
     * 增加歌手
     * 并添加歌手的头像
     * @param singer 歌手
     * @return 插入结果
     */
    boolean addSinger(Singer singer);

    /*
     * 更新歌手的信息
     * */
    boolean updateSinger(Singer singer);

    /**
     * 删除
     */
    boolean deleteSinger(Integer id);

    /**
     * 查询单个歌手
     */
    Singer findSinger(Integer id);

    /*将歌手的id和歌曲的id插入到关联表中去*/
    boolean addSingerIdSongId(Integer singer_id, Long song_id);

    /**
     * 根据歌手名进行模糊查询
     * @param name 歌手名
     * @return 返回查询的结果
     */
    IPage<Singer> singerOfName(Integer pageNum, Integer pageSize, String name);

    /*分页查询歌曲的信息*/
    IPage<Singer> getSingerByPage(Integer pageNum, Integer pageSize);

    /*根据歌手的性别来进行模糊查询*/
    IPage<Singer>  singerOfSex(Integer pageNum, Integer pageSize,Integer sex);

    /*根据歌手的地域查询歌手的信息*/
    IPage<Singer> getSingerByLocation(Integer pageNum, Integer pageSize, String location);

    /*根据歌手的年龄查询歌手信息*/
    IPage<Singer> getSingerByAge(Integer pageNum, Integer pageSize, int age);


}

