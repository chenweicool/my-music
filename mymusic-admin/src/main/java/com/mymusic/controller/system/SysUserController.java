package com.mymusic.controller.system;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mymusic.common.domain.ResultBean;
import com.mymusic.common.domain.ResultVo;
import com.mymusic.common.domain.StatisticsVo;
import com.mymusic.common.domain.SysUserVO;
import com.mymusic.common.exception.AjaxResponse;
import com.mymusic.common.utils.Constants;
import com.mymusic.domain.SongList;
import com.mymusic.domain.SysUser;
import com.mymusic.domain.SysUserOrg;
import com.mymusic.formvo.SysUserVo;
import com.mymusic.service.SongListService;
import com.mymusic.service.SysUserSonglistService;
import com.mymusic.service.UpdatePictureOrFileService;
import com.mymusic.service.impl.SysUserServiceImpl;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Resource
    private UpdatePictureOrFileService pictureOrFileService;

    //根据登录用户名查询用户信息
    @GetMapping(value = "/info")
    public SysUserVO info(@RequestParam("username") String username) {
        return sysuserService.getUserByUserName(username);
    }

    @PostMapping("/getuser")
    public SysUser getUserById(@RequestParam("userId") Long userId) {
        return  sysuserService.getUserById(userId);
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
     * 用户更新的操作
     * @param sysUser 用户的信息
     * @return
     */
    @PostMapping(value = "/update")
    public AjaxResponse update(@RequestBody SysUser sysUser) {
        sysuserService.updateUser(sysUser);
        return AjaxResponse.success("更新用户成功");
    }
    //用户管理：新增
    @PostMapping(value = "/add")
    public AjaxResponse add(@RequestBody SysUser sysUser) {
        return sysuserService.addUser(sysUser);
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

    /**
     * 更新用户的头像信息
     * @return 返回歌曲的海报信息
     */
    @PostMapping("/updatePicture")
    public AjaxResponse updateSingerPic(@RequestParam("file") MultipartFile avatarFile, @RequestParam("id") Long id){
        if (avatarFile.isEmpty()) {
            return AjaxResponse.error("上传的文件信息不能为空");
        }
        String fileName = System.currentTimeMillis() + avatarFile.getOriginalFilename();
        String fileKey = Constants.PICTURE_FILE+fileName;
        String result = pictureOrFileService.updatePictureOrFile(avatarFile, fileKey);
        if(!StringUtils.isEmpty(result)){
            SysUser sysUser = sysuserService.getUserById(id);
            sysUser.setAvator(fileKey);
            boolean updateResult = sysuserService.updateUser(sysUser);
            ResultVo resultVo = new ResultVo();
            resultVo.setMessage("更新图片成功");
            resultVo.setResult(fileKey);
            if(updateResult){
                return AjaxResponse.success(resultVo);
            }else{
                return AjaxResponse.error("更新图片失败");
            }
        }else{
            return AjaxResponse.error("上传图片失败");
        }
    }

    //实现用户性别统计
    @RequestMapping("/statistic/sex")
    public Object getStaticSex(){
        List<StatisticsVo> listSex = sysuserService.getStaticSex();
        return  listSex;
    }

    /**
     * 用户地域信息的统计
     * @return
     */
    @RequestMapping("/statistic/location")
    public AjaxResponse getLocation(){
        List<StatisticsVo> listLocation =  sysuserService.getStaticLocation();
        return AjaxResponse.success(listLocation);
    }

    /**
     * 得到用户的总的人数
     * @return
     */
    @RequestMapping("/statistic/total")
    public AjaxResponse getTotalUsers(){
        Long getTotalUsers = sysuserService.getTotalUsers();
        return AjaxResponse.success(getTotalUsers);
    }

    /**
     * 得到今日总的注册人数
     */
    @RequestMapping("/statistic/newUsers")
    public AjaxResponse addNewUsers(){
        Long  getNewUsers = sysuserService.addNewUsers();
        return AjaxResponse.success(getNewUsers);
    }
}

