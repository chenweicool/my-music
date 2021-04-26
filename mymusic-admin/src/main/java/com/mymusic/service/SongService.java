package com.mymusic.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mymusic.common.domain.SongVo;
import com.mymusic.domain.Song;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SongService {

    /*删除歌曲信息*/
    boolean deleteSong(Long id);

    /*插入歌曲的信息*/
    boolean insertSong(Song record, MultipartFile file);

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
}
