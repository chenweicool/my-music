package com.mymusic.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mymusic.common.domain.SongVo;
import com.mymusic.common.domain.StatisticsVo;
import com.mymusic.common.exception.AjaxResponse;
import com.mymusic.domain.Song;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SongService {

    /*删除歌曲信息*/
    boolean deleteSong(Long id);

    /*插入歌曲的信息*/
    boolean insertSong(Song record);

    /*根据歌曲的id进行查询*/
    Song selectSong(Long id);

    /*更新歌曲的信息*/
    boolean updateSong(Song record);

    /*分页查询的实现*/
    IPage<SongVo> selectSongByPage(Integer pageNum, Integer pageSize);

    /*根据歌曲的url返回歌曲的id*/
    Long selectSongByUrl(String songUrl);

    /**
     * 根据歌曲名或歌手名返回信息
     * @param pageNum 分页参数
     * @param pageSize 分页的大小
     * @param queryName 查询的参数
     * @return {@link SongVo}
     */
    IPage<SongVo> querySongBySongNameOrSingerName(Integer pageNum, Integer pageSize, String queryName);

    /*根据歌手id返回歌曲的信息*/
    IPage<SongVo> selectSongBySingerId(Integer pageNum, Integer pageSize,Integer singerId);


    /*根据歌单的id返回歌曲的信息*/
    IPage<SongVo> selectSongBySongListId(Integer pageNum, Integer pageSize,Integer parseInt);

    /**
     * 根据歌曲查询歌曲的信息
     * @param songName 歌曲的信息
     * @return
     */
    List<Song> songOfName(String songName);

    /*返回热门歌曲*/
    AjaxResponse getHotSong();

    /**
     * 根据个人信息返回推荐的音乐
     * @param userId
     * @return
     */
    AjaxResponse getRecommendSong(Long userId);

    /**
     * 返回用户的浏览历史记录信息
     * @param songIds 歌曲的id的信息
     * @return
     */
    AjaxResponse getHistorySong(List<Long> songIds);

    /*获取总的歌曲数量*/
    Long getTotalSongs();

    /*获取总的播放次数最高的歌曲*/
    List<StatisticsVo> getMaxPlaySongs();

    /*获取评论数最多的个歌曲*/
    List<StatisticsVo> getMaxComment();

    /*我的喜欢中最多的歌曲*/
    List<StatisticsVo> getMaxCollect();
}
