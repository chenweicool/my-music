package com.mymusic.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mymusic.common.domain.StatisticsVo;
import com.mymusic.domain.Singer;
import org.apache.ibatis.annotations.Param;

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

    /*插入歌手时，增加关联表中的记录的值*/
    int addSingerIdSongId(Integer singerId, Long songId);

    /*根据歌手的性别来进行模糊查询*/
    IPage<Singer>  singerOfSex(IPage<Singer> page,@Param("sex") Integer sex);
    /**
     * 根据歌手名进行模糊查询
     * @param name 歌手名
     * @return 返回查询的结果
     */
    IPage<Singer> singerOfName(IPage<Singer> page,@Param("name") String name);


    /*根据歌手的地域来查询*/
    IPage<Singer> getSingerByLocation(IPage<Singer> page, @Param("location") String location);

    /**
     * 根据歌手的年龄返回歌手的信息
     * @param page 具体的分页的大小
     * @param startTime 开始的时间范围
     * @param endTime 结束的时间范围
     * @return
     */
    IPage<Singer> getSingerByAge(IPage<Singer> page, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /*分页获取歌手信息*/
    IPage<Singer> getSingerByPage(IPage<Singer> page);

    /*获取前10的歌手信息*/
    List<Singer> getSingerHot();

    /*获取总的歌手数量*/
    Long getTotalSinger();

    /*获取歌手拥有歌曲的数量*/
    List<StatisticsVo> getMaxSongsOfSinger();

     /*获取歌手的性别数量*/
    List<StatisticsVo> getSexSingers();
}
