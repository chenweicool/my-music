package com.mymusic.service.impl;

import com.mymusic.common.enums.SongConsumerType;
import com.mymusic.common.exception.SongException;
import com.mymusic.domain.SongList;
import com.mymusic.mapper.SongListMapper;
import com.mymusic.model.SysUserSonglist;
import com.mymusic.service.SongListService;
import com.mymusic.service.SysUserSonglistService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class SongListServiceImpl implements SongListService {

    @Resource
    private SongListMapper songListMapper;

    @Resource
    private SysUserSonglistService sysUserSonglistService;

    /**
     * 这里的添加逻辑
     * 不但要添加到相应的表中去，还要添加到用户和歌单的关联的表中去。
     * @param songList
     * @return
     */
    @Override
    public boolean addSongList(SongList songList,Long userId) {
        SysUserSonglist sysUserSonglist = new SysUserSonglist();
        String uuid = UUID.randomUUID().toString();
        songList.setUuid(uuid);
        if(songListMapper.insert(songList) > 0){
           songList = songListMapper.findSongListByUUID(uuid);
            sysUserSonglist.setSonglistId(songList.getId());
            sysUserSonglist.setUserId(userId);
            return sysUserSonglistService.addSysUserSongList(sysUserSonglist);
        }else{
            throw new SongException("添加歌曲不成功");
        }
    }

    @Override
    public boolean addSongList(SongList songList) {
        String uuid = UUID.randomUUID().toString();
        songList.setUuid(uuid);
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

    /**
     * 删除之前查询一下数据库
     * 就是管理员可以删除一切的歌单的信息
     * @param id 歌单的id的信息
     * @return
     */
    @Override
    public boolean deleteSongList(Integer id) {
        SysUserSonglist sysUserSonglist = new SysUserSonglist();
        if(sysUserSonglistService.selectBySongList(id) != null){
            songListMapper.deleteByPrimaryKey(id);
            return sysUserSonglistService.deleteSysUserSongList(id);
        }else{
          return  songListMapper.deleteByPrimaryKey(id) >0;
        }
    }

    //TODO 优化，分页优化
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

    /**
     * 根据用户的Id查询用户创建的歌单信息
     * @param userId
     * @return
     */
    @Override
    public List<SongList> findMySongList(Long userId) {
        List<SongList> mySongList = songListMapper.findMySongList(userId);
        return mySongList;
    }
}
