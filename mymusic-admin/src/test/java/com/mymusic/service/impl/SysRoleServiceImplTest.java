package com.mymusic.service.impl;

import com.mymusic.domain.SysRole;
import net.bytebuddy.asm.Advice;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;

import static org.junit.Assert.*;

public class SysRoleServiceImplTest extends BaseTest {

    @Autowired
    private  SysRoleServiceImpl sysRoleService;

    @Test
    public void queryRoleByRoleCodeTest() {
        String roleCode = "common";
        SysRole sysRole = sysRoleService.queryRoleByRoleCode(roleCode);
        Assert.assertNotNull(sysRole);
    }

    /**|
     * 测试用户默认授权。
     */
    @Test
    public void insertSysUserRoleTest(){
        Long roleId = 1353244952249323521l;
        Long userId = 1387787203609300994l;
        sysRoleService.updateUserRoleByUserId(userId, roleId);
    }
}