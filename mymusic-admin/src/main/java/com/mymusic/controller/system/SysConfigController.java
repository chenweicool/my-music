package com.mymusic.controller.system;


import com.mymusic.common.exception.AjaxResponse;
import com.mymusic.config.DbLoadSysConfig;
import com.mymusic.domain.SysConfig;
import com.mymusic.service.impl.SysConfigServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 系统全局配置参数 前端控制器
 * </p>
 *
 * @author chen
 * @since 2021-01-16
 */

@Api(tags = "系统的配置信息表")
@RestController
@RequestMapping("/sysconfig")
public class SysConfigController {
    @Resource
    private DbLoadSysConfig dbLoadSysConfig;

    @PostMapping(value = "/all")
    public List<SysConfig> all() {
        return dbLoadSysConfig.getSysConfigList();
    }

    @PostMapping(value = "/refresh")
    public List<SysConfig> refresh() {
        return dbLoadSysConfig.getSysConfigList();
    }


    @Resource
    private SysConfigServiceImpl sysconfigService;

    @PostMapping(value = "/query")
    public List<SysConfig> query(@RequestParam("configLike") String configLike) {
        return sysconfigService.queryConfigs(configLike);
    }

    @PostMapping(value = "/update")
    public AjaxResponse update(@RequestBody SysConfig sysConfig) {
        sysconfigService.updateConfig(sysConfig);
        return AjaxResponse.success("更新配置成功！");
    }

    @PostMapping(value = "/add")
    public AjaxResponse add(@RequestBody SysConfig sysConfig) {
        sysconfigService.addConfig(sysConfig);
        return AjaxResponse.success("新增配置成功！");
    }

    @PostMapping(value = "/delete")
    public AjaxResponse delete(@RequestParam Long configId) {
        sysconfigService.deleteConfig(configId);
        return AjaxResponse.success("删除配置成功!");
    }

}

