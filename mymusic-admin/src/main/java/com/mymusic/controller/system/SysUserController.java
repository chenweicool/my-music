package com.mymusic.controller.system;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mymusic.common.exception.AjaxResponse;
import com.mymusic.domain.SysUser;
import com.mymusic.domain.SysUserOrg;
import com.mymusic.formvo.SysUserVo;
import com.mymusic.service.impl.SysUserServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author chen
 * @since 2021-01-16
 */
@Api(tags = "综合用户的信息表")
@RestController
@RequestMapping("/sysuser")
public class SysUserController {
    @Resource
    private SysUserServiceImpl sysuserService;

    //根据登录用户名查询用户信息
    @GetMapping(value = "/info")
    public SysUser info(@RequestParam("username") String username) {
        return sysuserService.getUserByUserName(username);
    }

    @PostMapping("/query")
    public IPage<SysUserOrg> query(@RequestParam("avator") String avator ,
                                   @RequestParam("sex") Integer sex ,
                                   @RequestParam("introduction") String introduction,
                                   @RequestParam("birth") Date birth,
                                   @RequestParam("location") Boolean location,
                                   @RequestParam("orgId") Long orgId ,
                                   @RequestParam("username") String username ,
                                   @RequestParam("phone") String phone,
                                   @RequestParam("email") String email,
                                   @RequestParam("enabled") Boolean enabled,
                                   @RequestParam("createStartTime") Date createStartTime,
                                   @RequestParam("createEndTime") Date createEndTime,
                                   @RequestParam("pageNum") Integer pageNum,
                                   @RequestParam("pageSize") Integer pageSize) {
        return sysuserService.queryUser(avator,sex,introduction,birth,location,orgId,username,phone,email,enabled,
                createStartTime, createEndTime,
                pageNum,pageSize);
    }
    /**
     * 查询所有的用户的信息
     * @return 用户的信息
     */
    @GetMapping("/allUser")
    public List<SysUserVo> getAllUser(){
        return sysuserService.getAllUser();
    }
    /**
     * 用户更新的操作
     * @param sysUser 用户的信息
     * @return
     */
    @PostMapping(value = "/update")
    public AjaxResponse update(@RequestBody SysUser sysUser) {
        sysuserService.updateUser(sysUser);
        return AjaxResponse.success("更新用户成功！");
    }
    //用户管理：新增
    @PostMapping(value = "/add")
    public AjaxResponse add(@RequestBody SysUser sysUser) {
        sysuserService.addUser(sysUser);
        return AjaxResponse.success("新增用户成功！");
    }

    //用户管理：删除
    @PostMapping(value = "/delete")
    public AjaxResponse delete(@RequestParam Long userId) {
        sysuserService.deleteUser(userId);
        return AjaxResponse.success("删除用户成功!");
    }

    //用户管理：重置密码
    @PostMapping(value = "/pwd/reset")
    public AjaxResponse pwdreset(@RequestParam Long userId) {
        sysuserService.pwdreset(userId);
        return AjaxResponse.success("重置密码成功!");
    }

    //判断登录用户密码是否是默认密码
    @PostMapping(value = "/pwd/isdefault")
        public Boolean isdefault(@RequestParam String username) {

            return sysuserService.isdefault(username);
        }
        //修改密码
    @PostMapping(value = "/pwd/change")
    public AjaxResponse pwdchange(@RequestParam String username,
                                  @RequestParam String oldPass,
                                  @RequestParam String newPass) {
        sysuserService.changePwd(username,oldPass,newPass);
        return AjaxResponse.success("修改密码成功!");
    }

    //用户管理：更新用户激活状态
    @PostMapping(value = "/enabled/change")
    public AjaxResponse update(@RequestParam Long userId,
                               @RequestParam Boolean enabled) {
        sysuserService.updateEnabled(userId, enabled);
        return AjaxResponse.success("用户状态更新成功！");
    }
}

