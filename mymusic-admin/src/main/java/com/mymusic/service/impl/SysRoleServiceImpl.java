package com.mymusic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mymusic.domain.SysRole;
import com.mymusic.domain.SysUserRole;
import com.mymusic.mapper.MySystemMapper;
import com.mymusic.mapper.SysRoleMapper;
import com.mymusic.mapper.SysUserRoleMapper;
import com.mymusic.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author chen
 * @since 2021-01-16
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private MySystemMapper mySystemMapper;
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    /**
     * 根据参数查询角色记录
     * @param roleLik 角色编码 或角色描述 或角色名称模糊查询
     * @return 角色记录列表
     */
    public List<SysRole> queryRoles(String roleLik) {
        QueryWrapper<SysRole> query = new QueryWrapper<>();
        query.like(StringUtils.isNotEmpty(roleLik),"role_code",roleLik)
                .or()
                .like(StringUtils.isNotEmpty(roleLik),"role_desc",roleLik)
                .or()
                .like(StringUtils.isNotEmpty(roleLik),"role_name",roleLik);

        return sysRoleMapper.selectList(query);
    }

    public void updateRole(SysRole sysrole){
        Assert.isTrue(sysrole.getId() != null,
                "更新数据必须指定数据更新条件（主键）");
        sysRoleMapper.updateById(sysrole);
    }

    public void addRole(SysRole sysrole){
        sysrole.setStatus(false); //是否禁用:false
        sysRoleMapper.insert(sysrole);
    }

    public void deleteRole(Long id){
        Assert.isTrue(id != null,
                "删除数据必须指定数据删除条件（主键）");
        sysRoleMapper.deleteById(id);
    }

    //获取角色记录及某用户勾选角色记录
    public Map<String,Object> getRolesAndChecked(Long userId){
        Assert.isTrue(userId != null,
                "获取角色信息必须传入用户id作为参数");

        Map<String,Object> ret = new HashMap<>();
        //所有角色记录
        ret.put("roleDatas",sysRoleMapper.selectList(null));
        //某用户具有的角色id列表
        ret.put("checkedRoleIds",mySystemMapper.getCheckedRoleIds(userId));
        return ret;
    }

    //保存某用户勾选的角色id数据
    @Transactional
    public void saveCheckedKeys(Long userId,List<Long> checkedIds){
        sysUserRoleMapper.delete(new UpdateWrapper<SysUserRole>()
                .eq("user_id",userId));
        mySystemMapper.insertUserRoleIds(userId,checkedIds);
    }

    //角色管理：更新角色的禁用状态
    public void updateStatus(Long id,Boolean status){
        Assert.isTrue(id != null, "修改操作必须带主键");
        SysRole sysRole = new SysRole();
        sysRole.setId(id);
        sysRole.setStatus(status);
        sysRoleMapper.updateById(sysRole);
    }
}
