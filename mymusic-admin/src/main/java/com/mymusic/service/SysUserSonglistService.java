package com.mymusic.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mymusic.mapper.SysUserSonglistMapper;
import com.mymusic.model.SysUserSonglist;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysUserSonglistService {

    @Resource
    private SysUserSonglistMapper sysUserSonglistMapper;

    //用户增加一个歌单时
 public boolean addSysUserSongList(SysUserSonglist sysUserSonglist){
     return sysUserSonglistMapper.insert(sysUserSonglist) > 0;
 }
    // 删除一个自己创建的一个歌单
public boolean deleteSysUserSongList(Integer sysUserSonglist){
    QueryWrapper<SysUserSonglist> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("songlist_id",sysUserSonglist);
    return sysUserSonglistMapper.delete(queryWrapper) > 0;
}

    // 查询用户创建的所有的歌单信息
    public List<SysUserSonglist> selectAllSysUserSongList(){
        return sysUserSonglistMapper.selectList(null);
    }

    public SysUserSonglist selectBySongList(Integer id) {
        QueryWrapper<SysUserSonglist> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("songlist_id", id);
        return sysUserSonglistMapper.selectOne(queryWrapper);
    }
}
