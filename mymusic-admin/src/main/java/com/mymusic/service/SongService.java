package com.mymusic.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mymusic.domain.Song;
import java.util.List;

public interface SongService {
    /*删除歌手的信息*/
    boolean deleteSong(Long id);

    /*插入歌手的信息*/
    boolean insertSong(Song record);

    /*根据歌手的id进行查询*/
    Song selectSong(Long id);

    /*查询所有的歌手*/
    List<Song> selectAll();

    /*更新歌手的信息*/
    boolean updateSong(Song record);

    /*根据歌曲的名字查询歌曲*/
    Song songOfName(String name);

    /*更新歌手的图片的信息*/
    boolean updateSongPic(Song song);

    /*更具歌手id返回歌曲的信息*/
    List<Song> selectSongBySingerId(Integer singerId);

    /*分页查询的实现*/
    IPage<Song> querySong(String name, String introduction, String lyric, Integer pageNum, Integer pageSize);

    /*根据歌曲的url返回歌曲的id*/
    Long selectSongByUrl(String songUrl);

    /*根据歌单的id返回歌曲的信息*/
    List<Song> selectSongBySongListId(Integer parseInt);
}
