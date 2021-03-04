package com.mymusic.service.impl;

import com.mymusic.domain.SysApi;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.*;

public class SysApiServiceImplTest extends BaseTest {

    @Resource
    private SysApiServiceImpl sysApiService;

    @Test
    public void updateApi() {
        SysApi sysApi = new SysApi();
         sysApi.setId(1354364976403443700L);
        sysApi.setApiPids("[0],[1],[2]");
        sysApi.setApiPid(2L);
        sysApi. setSort(43);
        sysApiService.updateApi(sysApi);
    }

    @Test
    public void deleteApi() {
    }
}