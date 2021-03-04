package com.mymusic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mymusic.domain.SysDict;
import com.mymusic.mapper.SysDictMapper;
import com.mymusic.service.SysDictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 数据字典表 服务实现类
 * </p>
 *
 * @author chen
 * @since 2021-01-16
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {
    @Resource
    private SysDictMapper sysDictMapper;

    /**
     * 查询所有
     */
    public List<SysDict> all(){
        return  sysDictMapper.selectList(null);
    }

    /**
     * 根据参数查询
     * @param groupName 分组名称
     * @param groupCode 分组编码
     */
    public List<SysDict> query(
            String groupName,
            String groupCode ) {
        LambdaQueryWrapper<SysDict> lambdaQ = Wrappers.lambdaQuery();
        lambdaQ
                .like(StringUtils.isNotEmpty(groupName),SysDict::getGroupName,groupName)
                .like(StringUtils.isNotEmpty(groupCode),SysDict::getGroupCode,groupCode);

        return sysDictMapper.selectList(lambdaQ);
    }

    /**
     * 根据id更新数据字典项
     * @param sysDict 更新实体(包含id)
     */
    public void update(SysDict sysDict){
        Assert.isTrue(sysDict.getId() != null,
                "更新数据必须指定数据更新条件（主键）");

        sysDictMapper.updateById(sysDict);
    }

    /**
     * 新增数据字典项
     * @param sysDict 新增实体
     */
    public void add(SysDict sysDict){
        sysDict.setCreateTime(new Date());
        sysDictMapper.insert(sysDict);
    }

    /**
     * 根据id删除数据字典项
     * @param id  删除项的id
     */
    public void delete(Long id){
        Assert.isTrue(id != null,
                "删除数据必须指定数据删除条件（主键）");
        sysDictMapper.deleteById(id);
    }

}
