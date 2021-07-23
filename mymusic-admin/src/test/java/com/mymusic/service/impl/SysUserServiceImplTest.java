package com.mymusic.service.impl;

import com.mymusic.common.domain.StatisticsVo;
import org.junit.Assert;
import org.junit.Test;
import javax.annotation.Resource;
import java.util.List;


/**
 * 测试用户的信息
 */
public class SysUserServiceImplTest extends BaseTest {

    @Resource
    private SysUserServiceImpl sysUserService;

    @Test
    public void getStaticSex() {
        List<StatisticsVo> staticSex = sysUserService.getStaticSex();
        for (StatisticsVo sex : staticSex) {
            System.out.println(sex.toString());
        }
        Assert.assertNotNull(staticSex);
    }

    @Test
    public void getStaticLocation() {
        List<StatisticsVo> staticLocation = sysUserService.getStaticLocation();
        for (StatisticsVo statisticsVo : staticLocation) {
            System.out.println(statisticsVo.toString());
        }
        Assert.assertNotNull(staticLocation);
    }

    @Test
    public void getTotalUsers() {
        Long totalUsers = sysUserService.getTotalUsers();
        System.out.println(totalUsers);
        Assert.assertNotNull(totalUsers);
    }

    @Test
    public void addNewUsers() {
        Long newUsers = sysUserService.addNewUsers();
        Assert.assertNotNull(newUsers);
    }
}