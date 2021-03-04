package com.mymusic.service.impl;

import com.mymusic.domain.SongList;
import com.mymusic.common.enums.SongConsumerType;
import com.mymusic.common.exception.SongException;
import com.mymusic.mapper.SongListMapper;
import com.mymusic.service.SongListService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class SongListServiceImpl implements SongListService {

    @Resource
    private SongListMapper songListMapper;

    @Override
    public boolean addSongList(SongList songList) {
        return songListMapper.insert(songList) > 0;
    }

    @Override
    public boolean updateSongListMsg(SongList songList) {
        SongList songList1 = songListMapper.selectByPrimaryKey(songList.getId());
        if (songList1 == null) {
            throw new SongException(SongConsumerType.SONGLIST_NOT_EXIST);
        }
        return songListMapper.updateByPrimaryKey(songList)>0;
    }

    @Override
    public boolean updateSongListImg(SongList songList) {
        SongList songList1 = songListMapper.selectByPrimaryKey(songList.getId());
        if (songList1 == null) {
            throw new SongException(SongConsumerType.SONGLIST_NOT_EXIST);
        }
        return songListMapper.updateSongListImg(songList) >0 ;
    }

    @Override
    public boolean deleteSongList(Integer id) {
        SongList songList = songListMapper.selectByPrimaryKey(id);
        if (songList == null) {
            throw new SongException(SongConsumerType.SONGLIST_NOT_EXIST);
        }
        return songListMapper.deleteByPrimaryKey(id) >0;
    }

    //TODO 如果数据量很大的，查询所有的话，就会很费劲
    @Override
    public List<SongList> allSongList() {
        return songListMapper.selectAll();
    }

    @Override
    public List<SongList> likeTitle(String title) {
        List<SongList> getTitleList = songListMapper.likeTitle(title);
        if(getTitleList == null){
            throw new SongException(SongConsumerType.SONGLIST_TITLE_EXIST.getMessage());
        }
        return getTitleList;
    }

    @Override
    public List<SongList> likeStyle(String style) {
        List<SongList> getTitleList = songListMapper.likeStyle(style);
        if(getTitleList == null){
            throw new SongException(SongConsumerType.SONGLIST_STYLE_EXIST.getMessage());
        }
        return getTitleList;
    }

    @Override
    public List<SongList> songListOfTitle(String title) {
        List<SongList> getTitleList = songListMapper.songListOfTitle(title);
        if(getTitleList == null){
            throw new SongException(SongConsumerType.SONGLIST_TITLE_EXIST.getMessage());
        }
        return getTitleList;
    }

    @Override
    public SongList songListById(Integer  id) {
        SongList songList = songListMapper.selectByPrimaryKey(id);
        if (songList == null) {
            throw new SongException(SongConsumerType.SONGLIST_NOT_EXIST);
        }
        return songList;
    }
}
