package com.mymusic.service.impl;

import com.mymusic.domain.ListSong;
import com.mymusic.domain.SongList;
import com.mymusic.common.enums.SongConsumerType;
import com.mymusic.common.exception.SongException;
import com.mymusic.mapper.ListSongMapper;
import com.mymusic.service.ListSongService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/*歌单中歌曲的service层管理实现*/
@Service
public class ListSongServiceImpl implements ListSongService {

    @Resource
    private ListSongMapper listSongMapper;

    @Override
    public boolean addListSong(ListSong listSong) {
        return listSongMapper.insert(listSong) > 0;
    }

    @Override
    public boolean updateListSongMsg(ListSong listSong) {
        return listSongMapper.updateListSongMsg(listSong) > 0;
    }

    @Override
    public boolean deleteListSong(Integer songId) {
        ListSong listSong = listSongMapper.selectBySongId(songId);
        if (listSong == null) {
            throw new SongException(SongConsumerType.SONG_NOT_EXIST.getMessage());
        }
        return listSongMapper.deleteListSong(songId) > 0;
    }

    @Override
    public boolean deleteBySongIdAndSongListId(Integer songId, Integer songListId) {
        ListSong listSong = listSongMapper.selectBySongId(songId);
        if (listSong == null) {
            throw new SongException(SongConsumerType.SONG_NOT_EXIST.getMessage());
        }
        return listSongMapper.deleteBySongIdAndSongListId(songId,songListId) > 0 ;
    }

    @Override
    public List<ListSong> allListSong() {
        return listSongMapper.allListSong();
    }

    /*根据歌单中id查询歌曲的信息*/
    @Override
    public List<ListSong> listSongOfSongId(Integer songListId) {
        return listSongMapper.listSongOfSongId(songListId);
    }

}
