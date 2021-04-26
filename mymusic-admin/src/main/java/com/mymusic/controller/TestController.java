package com.mymusic.controller;

import com.mymusic.common.config.CosProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用来测试使用的
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private CosProperties cosProperties;

    @RequestMapping("/gethost")
    public String getHostName(){
        String hostName = cosProperties.getHostName();
        return hostName;
    }
}
