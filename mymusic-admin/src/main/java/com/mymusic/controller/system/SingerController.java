package com.mymusic.controller.system;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mymusic.common.exception.AjaxResponse;
import com.mymusic.common.utils.Constants;
import com.mymusic.domain.Singer;
import com.mymusic.service.SingerService;
import com.mymusic.service.SongService;
import com.mymusic.service.UpdatePictureOrFileService;
import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = "歌手的接口")
@RestController
@RequestMapping("/singer")
@Slf4j
public class SingerController {

    @Resource
    private SingerService singerService;

    @Resource
    private UpdatePictureOrFileService pictureOrFileService;


    /**
     * 添加歌手
     * @param singer 歌手的信息
     * @return 添加的结果
     */
    @ResponseBody
    @RequestMapping(value = "/addSinger", method = RequestMethod.POST)
    public AjaxResponse addSinger(@RequestBody Singer singer){
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
    @PostMapping("/updatePicture")
    public AjaxResponse updateSingerPic(@RequestParam("file") MultipartFile avatarFile, @RequestParam("id") Integer id){
        if (avatarFile.isEmpty()) {
            return AjaxResponse.error("上传的文件信息不能为空");
        }
        String fileName = System.currentTimeMillis() + avatarFile.getOriginalFilename();
        String fileKey = Constants.PICTURE_FILE+fileName;
        String result = pictureOrFileService.updatePictureOrFile(avatarFile, fileKey);
        if(!StringUtils.isEmpty(result)){
            Singer singer = singerService.findSinger(id);
            singer.setPic(fileKey);
            boolean updateResult = singerService.updateSinger(singer);
            if(updateResult){
                return AjaxResponse.success("更新图片信息成功");
            }else{
                return AjaxResponse.error("更新图片失败");
            }
        }else{
            return AjaxResponse.error("上传图片失败");
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

    /*根据歌手的热度来显示歌手 显示10个歌手*/
    @RequestMapping(value = "/getSingerHot", method = RequestMethod.GET)
    public  AjaxResponse getSingerHot(){
       List<Singer> singerList =  singerService.getSingerHot();
       return AjaxResponse.success(singerList);
    }

}
