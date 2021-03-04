package com.mymusic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mymusic.common.exception.CustomException;
import com.mymusic.common.exception.CustomExceptionType;
import com.mymusic.domain.SysConfig;
import com.mymusic.mapper.SysConfigMapper;
import com.mymusic.service.SysConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 系统全局配置参数 服务实现类
 * </p>
 *
 * @author chen
 * @since 2021-01-16
 */
@Service
public class SysConfigServiceImpl implements  SysConfigService {
    @Resource
    private SysConfigMapper sysConfigMapper;

    public List<SysConfig> queryConfigs(String configLik) {
        QueryWrapper<SysConfig> query = new QueryWrapper<>();
        query.like(StringUtils.isNotEmpty(configLik),"param_name",configLik)
                .or()
                .like(StringUtils.isNotEmpty(configLik),"param_key",configLik);

        return sysConfigMapper.selectList(query);
    }

    public void updateConfig(SysConfig sysconfig){
        if(sysconfig.getId() == null){
            throw new CustomException(CustomExceptionType.USER_INPUT_ERROR,
                    "修改操作必须带主键");
        }else{
            sysConfigMapper.updateById(sysconfig);
        }
    }

    public void addConfig(SysConfig sysconfig){
        sysconfig.setCreateTime(new Date());
        sysConfigMapper.insert(sysconfig);
    }

    public void deleteConfig(Long configId){
        sysConfigMapper.deleteById(configId);
    }

}
