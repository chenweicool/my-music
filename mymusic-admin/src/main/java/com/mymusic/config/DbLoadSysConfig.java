package com.mymusic.config;

import com.mymusic.domain.SysConfig;
import com.mymusic.mapper.SysConfigMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * 解决了包引不进来的问题
 * 怎么解决的不知道，但是解决为题必须多坚持一下
 */
@Component
public class DbLoadSysConfig implements CommandLineRunner {
    @Resource
    private SysConfigMapper sysConfigMapper;

    private List<SysConfig> sysConfigList;

    //根据参数key，获取参数值
    public  String getConfigItem(String paramKey){
        Optional<SysConfig> temp =  sysConfigList.stream()
                .filter(str -> str.getParamKey().equals(paramKey))
                .findFirst();

        return temp.orElse(new SysConfig()).getParamValue();
    }

    //应用启动加载参数配置
    @Override
    public void run(String... args) throws Exception {
       sysConfigList = sysConfigMapper.selectList(null);
    }

    //获取所有参数配置项
    public  List<SysConfig> getSysConfigList() {
        sysConfigList = sysConfigMapper.selectList(null);
        return sysConfigList;
    }
}
