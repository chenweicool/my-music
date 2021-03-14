package com.mymusic.service;

import com.mymusic.model.SysUserSonglist;
import com.mymusic.service.impl.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

public class SysUserSonglistServiceTest extends BaseTest {

    @Resource
    private SysUserSonglistService sysUserSonglistService;

    @Test
    @Transactional
    public void addSysUserSongList() {
        SysUserSonglist sysUserSonglist = new SysUserSonglist();
        sysUserSonglist.setUserId(1297873308628307970l);
        sysUserSonglist.setSonglistId(5);
        boolean flag = sysUserSonglistService.addSysUserSongList(sysUserSonglist);
        Assert.assertEquals(true, flag);
    }

    @Test
    @Transactional
    public void deleteSysUserSongList() {
        boolean flag = sysUserSonglistService.deleteSysUserSongList(5);
        Assert.assertEquals(true, flag);
    }

    @Test
    public void selectAllSysUserSongList() {
        List<SysUserSonglist> getAllUserSongList = sysUserSonglistService.selectAllSysUserSongList();
        Assert.assertNotNull(getAllUserSongList);
    }
}