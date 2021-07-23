package com.mymusic.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mymusic.common.domain.SongToSongList;
import com.mymusic.common.domain.StatisticsVo;
import com.mymusic.common.enums.SongConsumerType;
import com.mymusic.common.exception.AjaxResponse;
import com.mymusic.common.exception.SongException;
import com.mymusic.common.utils.Constants;
import com.mymusic.common.utils.ParameterCheckUtils;
import com.mymusic.domain.SongList;
import com.mymusic.mapper.SongListMapper;
import com.mymusic.model.SysUserSonglist;
import com.mymusic.service.SongListService;
import com.mymusic.service.SysUserSonglistService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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
    @Deprecated
    @Override
    public boolean addSongListUserId(SongList songList,Long userId) {
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
        ParameterCheckUtils.checkParamIsBlank(songList.getUserId());
        SysUserSonglist sysUserSonglist = new SysUserSonglist();
        if (StringUtils.isEmpty(songList.getPic())) {
             songList.setPic(Constants.DEFAULT_PIC);
        }
        if(StringUtils.isEmpty(songList.getStyle())){
            songList.setStyle("未知");
        }
        songList.setCreateTime(new Date());
        songList.setUpdateTime(new Date());
        String uuid = UUID.randomUUID().toString();
        songList.setUuid(uuid);
        songList.setType(1);
        if(songListMapper.insert(songList) > 0){
            SongList  songListDb = songListMapper.findSongListByUUID(uuid);
            //addSongListUserId(songListDb,songList.getUserId());
            sysUserSonglist.setSonglistId(songListDb.getId());
            sysUserSonglist.setUserId(songList.getUserId());
            boolean result = sysUserSonglistService.addSysUserSongList(sysUserSonglist);
            return result ;
        }
        return false;
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
    @Override
    public SongList songListById(Integer  id) {
        SongList songList = songListMapper.selectByPrimaryKey(id);
        if (songList == null) {
            throw new SongException(SongConsumerType.SONGLIST_NOT_EXIST);
        }
        return songList;
    }

    @Override
    public IPage<SongList> getSongListByPage(Integer pageNum, Integer pageSize) {
        IPage<SongList> page = new Page<>(pageNum, pageSize);
        return songListMapper.getSongListByPage(page);
    }

    @Override
    public List<SongList> getSongListHot() {
        return songListMapper.getSongListHot();
    }


    @Override
    public IPage<SongList> likeStyle(Integer pageNum, Integer pageSize, String style) {
        IPage<SongList> page = new Page<>(pageNum, pageSize);
        IPage<SongList> getTitleList = songListMapper.likeStyle(page,style);
        if(getTitleList == null){
            throw new SongException(SongConsumerType.SONGLIST_STYLE_EXIST.getMessage());
        }
        return getTitleList;
    }

    @Override
    public IPage<SongList> songListOfTitle(Integer pageNum, Integer pageSize, String title) {
        IPage<SongList> page = new Page<>(pageNum, pageSize);
        IPage<SongList> getTitleList = songListMapper.songListOfTitle(page,title);
        if(getTitleList == null){
            throw new SongException(SongConsumerType.SONGLIST_TITLE_EXIST.getMessage());
        }
        return getTitleList;
    }

    /**
     * 根据用户的Id查询用户创建的歌单信息
     * @param userId
     * @return
     */
    @Override
    public IPage<SongList> findMySongList(Integer pageNum, Integer pageSize,Long userId) {
        IPage<SongList> page = new Page<>(pageNum, pageSize);
        IPage<SongList> mySongList = songListMapper.findMySongList(page,userId);
        return mySongList;
    }

    /*添加之前也是检查一下这个记录是否存在*/
    @Override
    public AjaxResponse addSongToSongList(Long songId,  List<Integer> songListId,Integer type) {
        ParameterCheckUtils.checkParamIsBlank(songId, songListId);
        List<SongToSongList> songToSongList = songListMapper.getSongToSongLists(songId,songListId,type);
        if (songToSongList.size()>0) {
            return AjaxResponse.success("已被收藏的歌单，不能重复收藏");
        }
        int result =  songListMapper.addSongToSongList(songId,songListId,type);
        if(result > 0){
            return AjaxResponse.success("收藏成功");
        }
        return AjaxResponse.error("收藏失败");
    }

    @Override
    public Long getTotalSongList() {
        return songListMapper.getTotalSongList();
    }

    @Override
    public List<StatisticsVo> getSongCateGory() {
        List<StatisticsVo> songCateGory = songListMapper.getSongCateGory();
        List<StatisticsVo> result = new ArrayList<>();
        for (StatisticsVo statisticsVo : songCateGory) {
            if (Integer.parseInt(statisticsVo.getName()) == 0) {
                  statisticsVo.setName("系统歌单");
            }else{
                statisticsVo.setName("用户自定义歌单");
            }
            result.add(statisticsVo);
        }
       return result;
    }


    /*删除前必须校验一下，删除记录是否存在*/
    @Override
    public AjaxResponse deleteSongToSongList(Long songId, Integer songListId,Integer type) {
        ParameterCheckUtils.checkParamIsBlank(songId, songListId);
       SongToSongList songToSongList = songListMapper.getSongToSongList(songId,songListId,type);
        if (Objects.isNull(songToSongList)) {
              return AjaxResponse.success("该歌曲还没被收藏,不能移除");
        }
       int result =  songListMapper.deleteSongToSongList(songId,songListId,type);
        if(result > 0){
            return AjaxResponse.success("移除收藏");
        }
        return AjaxResponse.error("移除收藏失败");
    }


}
