package com.mymusic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mymusic.ConvertService;
import com.mymusic.common.domain.SysUserVO;
import com.mymusic.common.exception.AjaxResponse;
import com.mymusic.common.utils.Constants;
import com.mymusic.common.utils.ParameterCheckUtils;
import com.mymusic.config.DbLoadSysConfig;
import com.mymusic.domain.SysRole;
import com.mymusic.domain.SysUser;
import com.mymusic.domain.SysUserOrg;
import com.mymusic.domain.server.Sys;
import com.mymusic.formvo.SysUserVo;
import com.mymusic.mapper.MySystemMapper;
import com.mymusic.mapper.SysRoleMapper;
import com.mymusic.mapper.SysUserMapper;
import com.mymusic.service.SysRoleService;
import com.mymusic.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author chen
 * @since 2021-01-16
 */

//TODO  增加用户的功能待实现，用户重置密码的实现
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private MySystemMapper mySystemMapper;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private DbLoadSysConfig dbLoadSysConfig;
    @Resource
    private ConvertService convertService;
    @Resource
    private SysRoleServiceImpl sysRoleService;

    //根据登录用户名查询用户信息
    public SysUserVO getUserByUserName(String userName){
        Assert.isTrue(StringUtils.isNotEmpty(userName),
                "查询参数用户名不存在");

        SysUser sysUser = sysUserMapper.selectOne(
                new QueryWrapper<SysUser>().eq("username",userName));
        if(sysUser != null){
            sysUser.setPassword("");  //清空密码信息
        }
        SysUserVO sysUserVO = convertService.convertSysUserVo(sysUser);
        return sysUserVO;
    }

    //用户管理：查询
    public IPage<SysUserOrg> queryUser( String avator,Integer sex, String introduction, Date birth, Boolean location, Long orgId,
                                       String username,
                                       String phone,
                                       String email,
                                       Boolean enabled,
                                       Date createStartTime,
                                       Date createEndTime,
                                       Integer pageNum,
                                       Integer pageSize){
        Page<SysUserOrg> page = new Page<> (pageNum,pageSize);   //查询第pageNum页，每页pageSize条数据
        return mySystemMapper.selectUser( page,avator,sex,introduction,birth,location,
                orgId,username,phone,email,enabled,
                createStartTime,
                createEndTime);
    }

    //用户管理：修改
    public boolean updateUser(SysUser sysuser){
        Assert.isTrue(sysuser.getId() != null,
                "修改操作必须带主键");
        sysuser.setUpdateTime(new Date());
        int i = sysUserMapper.updateById(sysuser);
        return i >0;
    }

    /**
     * todo 这里实现的非常的不好
     * @param sysUser {@link SysUser}
     */
    public AjaxResponse addUser(SysUser sysUser){
        ParameterCheckUtils.checkParamIsBlank(sysUser);
        ParameterCheckUtils.checkParamIsBlank(sysUser.getUsername());
        SysUserVO sysUserVO = new SysUserVO();
        SysUser sysUserDb = sysUserMapper.selectOne(
                new QueryWrapper<SysUser>().eq("username",sysUser.getUsername()));

        if (sysUserDb != null) {
            return AjaxResponse.success("该用户名已经存在");
        }

        sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        sysUser.setCreateTime(new Date());  //创建时间
        sysUser.setUpdateTime(new Date());  // 更新时间。默认是和创建的时间是一致的
        sysUser.setEnabled(true); //新增用户激活
        sysUser.setAvator(Constants.DEFAULT_PIC);

        //用户注册成功之后，在反查一下，完成普通角色的授权
        int result= sysUserMapper.insert(sysUser);
        if(result > 0){
            SysRole sysRole = sysRoleService.queryRoleByRoleCode(Constants.ROLE_COMMON);
            Long roleId = sysRole.getId();
             sysUserVO= getUserByUserName(sysUser.getUsername());
             if(Objects.isNull(sysUserVO)){
                 return AjaxResponse.error("插入用户信息异常");
             }
            Long userVOId = sysUserVO.getId();
            sysRoleService.updateUserRoleByUserId(userVOId,roleId);
            return AjaxResponse.success("注册信息成功");
        }else{
            return AjaxResponse.error("添加用户信息异常");
        }
    }

    //用户管理：删除
    public void deleteUser(Long userId){
        Assert.isTrue(userId != null, "删除操作必须带主键");
        sysUserMapper.deleteById(userId);
    }

    //用户管理：重置密码
    public void pwdreset(Long userId){
        Assert.isTrue(userId != null, "重置密码操作必须带主键");

        SysUser sysUser = new SysUser();
        sysUser.setId(userId);
        sysUser.setPassword(passwordEncoder.encode(
                dbLoadSysConfig.getConfigItem("user.init.password")
        ));
        sysUserMapper.updateById(sysUser);
    }

    //判断当前登录的用户密码是否是默认密码，如果是，会让他去修改
    public Boolean isdefault(String username){
        SysUser sysUser = sysUserMapper.selectOne(
                new QueryWrapper<SysUser>().eq("username",username));

        //判断数据库密码是否是默认密码
        return passwordEncoder.matches(
                dbLoadSysConfig.getConfigItem("user.init.password"),
                sysUser.getPassword());
    }

    //个人中心：修改密码
    public void changePwd(String username,String oldPass,String newPass){

        SysUser sysUser = sysUserMapper.selectOne(
                new QueryWrapper<SysUser>().eq("username",username));
        //判断旧密码是否正确
        boolean isMatch = passwordEncoder.matches(oldPass,sysUser.getPassword());
        Assert.isTrue(isMatch, "原密码输入错误，请确认后重新输入！");

        SysUser sysUserNew = new SysUser();
        sysUserNew.setId(sysUser.getId());
        sysUserNew.setPassword(passwordEncoder.encode(newPass));
        sysUserMapper.updateById(sysUserNew);
    }

    //用户管理：更新用户的激活状态
    public void updateEnabled(Long id,Boolean enabled){
        Assert.isTrue(id != null, "修改操作必须带主键");
        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        sysUser.setEnabled(enabled);
        sysUserMapper.updateById(sysUser);
    }

    public SysUser getUserById(Long userId) {
        return sysUserMapper.selectById(userId);
    }

//    public SysUserVO getUserByUserName(String userName) {
//        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("username", userName);
//        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
//        SysUserVO sysUserVO = convertService.convertSysUserVo(sysUser);
//        return sysUserVO;
//    }
}
