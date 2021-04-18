package com.mymusic.controller.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mymusic.common.enums.ResultCodeEnum;
import com.mymusic.common.exception.AjaxResponse;
import com.mymusic.common.utils.Constants;
import com.mymusic.common.utils.FileUtils;
import com.mymusic.domain.Singer;
import com.mymusic.domain.Song;
import com.mymusic.jwt.utils.TimeUtils;
import com.mymusic.service.SingerService;
import com.mymusic.service.SongService;
import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Api(tags = "歌手的接口")
@RestController
@RequestMapping("/singer")
@Slf4j
public class SingerController {

    @Resource
    private SingerService singerService;

    @Resource
    private SongService songService;

    @Value("${file.Access_Key}")
    private String Access_Key;

    @Value("${file.Secret_Key}")
    private String Secret_Key;

    /**
     * 添加歌手
     * todo  这个上传的问题没有解决
     * @param singer 歌手的信息
     * @param file 歌手的头像
     * @return 添加的结果
     */
    @ResponseBody
    @RequestMapping(value = "/addSinger", method = RequestMethod.POST)
    public AjaxResponse addSinger(@RequestBody Singer singer, @RequestParam(value = "file",required = false)
                            MultipartFile file){
        /*将歌手的头像上传至七牛云，将歌手的头像地址存储到数据库*/
//        String fileName = System.currentTimeMillis()+file.getOriginalFilename();
//        String fileKey = Constants.SINGER_PIC+fileName;
//        String storeAvatarPath = FileUtils.getAvatarPic(file, Access_Key, Secret_Key,fileKey);
//        singer.setPic(storeAvatarPath);
        boolean res = singerService.addSinger(singer);

        if (res){
            return AjaxResponse.success("添加歌手成功");
        }else {
            return AjaxResponse.error("添加失败");
        }
    }

    /**
     * 更新歌手的信息
     * @return   //TODO 这里需要优化更新
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public AjaxResponse updateSingerMsg(@RequestBody Singer singer){
        boolean res = singerService.updateSinger(singer);
        if (res) {
            return AjaxResponse.success("更新成功");
        } else {
            return  AjaxResponse.error("更新失败");
        }
    }

    /**
     * 更新歌手的头像信息
     * @return 返回用户的信息sh
     */
    @PostMapping("/updateSingerPic")
    public AjaxResponse updateSingerPic(@Param("file") MultipartFile avatarFile, @Param("id") Integer id){

        JSONObject jsonObject = new JSONObject();
        if (avatarFile.isEmpty()) {
            return AjaxResponse.error("上传的文件信息不能为空");
        }

        String fileName = System.currentTimeMillis() + avatarFile.getOriginalFilename();
        String filePath = System.getProperty("use.dir")+System.getProperty("file.separator")+"img";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdir();
        }
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String storeAvatorPath = "/img/"+fileName;

        try {
            avatarFile.transferTo(dest);
            Singer singer = new Singer();
            singer.setId(id);
            singer.setPic(storeAvatorPath);
            boolean res = singerService.updateSinger(singer);
            if (res){
                return AjaxResponse.success("上传成功");
            }else {
                return AjaxResponse.error("上传失败");
            }
        }catch (IOException e){
            return AjaxResponse.error("上传失败");
        }
    }

    /**
     * todo 删除需要判断一下
     * @param req
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public AjaxResponse deleteSinger(HttpServletRequest req){
        String id = req.getParameter("id");
        boolean result = singerService.deleteSinger(Integer.parseInt(id));
        if (result) {
            return AjaxResponse.success("删除成功");
        } else {
            return  AjaxResponse.error("删出失败");
        }
    }

    /*根据歌手的名字查询歌手*/
    @RequestMapping(value = "/getSingerByPage", method = RequestMethod.GET)
    public AjaxResponse getSingerByPage(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize){
        IPage<Singer> singer = singerService.getSingerByPage(pageNum,pageSize);
        return AjaxResponse.success(singer);
    }

    /*根据歌手的名字查询歌手*/
    @RequestMapping(value = "/getSingerBySingerName", method = RequestMethod.GET)
    public AjaxResponse getSingerName(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize,
                                      @RequestParam("singerName") String singerName){
        IPage<Singer> singer = singerService.singerOfName(pageNum,pageSize,singerName);
        return AjaxResponse.success(singer);
    }

    /*根据歌手地域查询歌手*/
    @RequestMapping(value = "/getSingerByLocation", method = RequestMethod.GET)
    public AjaxResponse getSingerByLocation(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize,
                                      @RequestParam("location") String location){
        IPage<Singer> singer = singerService.getSingerByLocation(pageNum,pageSize,location);
        return AjaxResponse.success(singer);
    }

    /*根据歌手的年龄查询歌手*/
    @RequestMapping(value = "/getSingerByAge", method = RequestMethod.GET)
    public AjaxResponse getSingerByAge(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize,
                                      @RequestParam("age") String age){
        IPage<Singer> singer = singerService.getSingerByAge(pageNum,pageSize,Integer.parseInt(age));
        return AjaxResponse.success(singer);
    }

    /*根据歌手的性别查询歌手*/
    @RequestMapping(value = "/getSingerBySex", method = RequestMethod.GET)
    public AjaxResponse singerOfSex(@RequestParam("pageNum") Integer pageNum,
                                    @RequestParam("pageSize") Integer pageSize,
                                    @RequestParam("sex") String sex){
        IPage<Singer> singer = singerService.singerOfSex(pageNum,pageSize,Integer.parseInt(sex));
        return AjaxResponse.success(singer);
    }

}
