package com.mymusic.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mymusic.common.enums.SongConsumerType;
import com.mymusic.common.exception.CustomException;
import com.mymusic.common.exception.SongException;
import com.mymusic.domain.Song;
import com.mymusic.mapper.SongMapper;
import com.mymusic.service.ListSongService;
import com.mymusic.service.SongService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SongServiceImpl implements SongService
{

    @Resource
    private SongMapper songMapper;

    @Override
    public boolean deleteSong(Long id) {
        Song song = songMapper.selectByPrimaryKey(id);
        if (song == null) {
            throw new SongException(SongConsumerType.SONG_NOT_EXIST.getMessage());
        }
        return songMapper.deleteByPrimaryKey(id) > 0;
    }

    /**
     * 插入歌曲的实现
     * @param record
     * @return
     */
    @Transactional
    @Override
    public boolean insertSong(Song record) {
        return songMapper.insert(record) > 0;
    }

    @Override
    public Song selectSong(Long  id) {
        Song song = songMapper.selectByPrimaryKey(id);
        if (song == null) {
            throw new SongException(SongConsumerType.SONG_NOT_EXIST.getMessage());
        }
        return song;
    }

    @Override
    public List<Song> selectAll() {
        return songMapper.selectAll();
    }

    /**
     * 更新歌曲
     * @param record
     * @return
     */
    @Override
    public boolean updateSong(Song record) {
        Long id = record.getId();
        Song songDB = songMapper.selectByPrimaryKey(id);
        if (songDB == null) {
            throw new SongException(SongConsumerType.SONG_NOT_EXIST.getMessage());
        }
        return songMapper.updateByPrimaryKey(record)>0;
    }

    @Override
    public List<Song> songOfName(String name) {
        List<Song> song = songMapper.songOfName(name);
        if (song == null) {
            throw new SongException(SongConsumerType.SONG_NOT_EXIST.getMessage());
        }
        return song;
    }

    @Override
    public boolean updateSongPic(Song song) {
        return songMapper.updateSongPic(song)>0;
    }

    @Override
    public List<Song> selectSongBySingerId(Integer singerId) {
        return songMapper.selectSongBySingerId(singerId);
    }

    /**
     * 分页查询歌曲的实现
     * @param name  歌曲名
     * @param introduction 歌曲的专辑
     * @param lyric 歌词
     * @param pageNum 分页的数量
     * @param pageSize 分页的大小
     * @return
     */
    @Override
    public IPage<Song>
    querySong(String name, String introduction, String lyric, Integer pageNum, Integer pageSize) {
        Page<Song> page = new Page<>(pageNum,pageSize); // 查询pagenum页，pagesize的数据
        return songMapper.selectSong(page,name,introduction,lyric);
    }

    /**
     * 根据歌曲的url返回歌曲的id
     * @param songUrl 歌曲播放地址
     * @return  返回歌曲的id
     */
    @Override
    public Long selectSongByUrl(String songUrl) {
        return songMapper.selectSongByUrl(songUrl);
    }

    @Override
    public List<Song> selectSongBySongListId(Integer songListId) {
        List<Song> songs = songMapper.selectSongBySongListId(songListId);
        return songs;
    }
}
