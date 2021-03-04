package com.mymusic.controller.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mymusic.common.enums.ResultCodeEnum;
import com.mymusic.common.exception.AjaxResponse;
import com.mymusic.common.utils.Constants;
import com.mymusic.common.utils.FileUtils;
import com.mymusic.domain.Singer;
import com.mymusic.domain.Song;
import com.mymusic.service.SingerService;
import com.mymusic.service.SongService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
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
     * @param singers 歌手的信息
     * @param file 歌手的头像
     * @return 添加的结果
     */
    @ResponseBody
    @RequestMapping(value = "/addSinger", method = RequestMethod.POST)
    public AjaxResponse addSinger(@RequestParam(value = "singer",required = false) String singers, @RequestParam(value = "file",required = false)
                            MultipartFile file){

        JSONObject jsonObject = new JSONObject();
        Singer singer = JSON.parseObject(singers, Singer.class);

        /*将歌手的头像上传至七牛云，将歌手的头像地址存储到数据库*/
        String fileName = System.currentTimeMillis()+file.getOriginalFilename();
        String fileKey = Constants.SINGER_PIC+fileName;
        String storeAvatarPath = FileUtils.getAvatarPic(file, Access_Key, Secret_Key,fileKey);
        singer.setPic(storeAvatarPath);
        boolean res = singerService.addSinger(singer);

        if (res){
            return AjaxResponse.success("添加歌手成功");
        }else {
            return AjaxResponse.error("添加失败");
        }
    }

    /**
     * //TODO  这个接口还没有授权
     * 歌手中添加歌曲
     * @param req  传递的参数
     * @param file 歌曲的文件
     * @return 插入成功的信息
     */
    @RequestMapping(value = "/song/addsong",method = RequestMethod.POST)
    public AjaxResponse addSingerSong(HttpServletRequest req, @RequestParam(value = "file", required = false)
            MultipartFile file){
        // 设置歌曲的信息
        String singer_id = req.getParameter("singerId").trim();
        String songName = req.getParameter("name").trim();
        String introduction = req.getParameter("introduction").trim();
        String lyric = req.getParameter("lyric").trim();
        String pic = Constants.DEFAULT_PIC;    // 默认的图片的地址的信息

        // 将歌曲文件上传至七牛云
        String SongKey = Constants.SONG_FILE + System.currentTimeMillis() + file.getOriginalFilename();  // 上传歌手文件的key的值
        String songUrl = FileUtils.getAvatarPic(file, Access_Key, Secret_Key, SongKey);  // 返回歌曲的播放地址信息

        /*完成歌手的信息的插入操作*/
        Song songDb = new Song();
        songDb.setSingerId(Integer.parseInt(singer_id));

        songDb.setName(songName);
        songDb.setIntroduction(introduction);
        songDb.setCreateTime(new Date());
        songDb.setUpdateTime(new Date());
        songDb.setPic(pic);
        songDb.setLyric(lyric);
        songDb.setUrl(songUrl);

        boolean res = songService.insertSong(songDb);

        if (res) {
             // 将歌手和歌曲进行关联
            Long songId = songService.selectSongByUrl(songUrl);
            singerService.addSingerIdSongId(Integer.parseInt(singer_id),songId);
            return AjaxResponse.success("添加成功");
        } else {
            return AjaxResponse.error("添加失败");
        }

    }

    /*更新歌手的信息*/
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public AjaxResponse updateSingerMsg(@RequestParam("singer") String singers){

        Singer singerVo = JSON.parseObject(singers, Singer.class);

        Singer singer = new Singer();
        singer.setId(singerVo.getId());
        singer.setName(singerVo.getName());
        singer.setSex(singerVo.getSex());
        singer.setBirth(singerVo.getBirth());
        singer.setLocation(singerVo.getLocation());
        singer.setIntroduction(singerVo.getIntroduction());
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

    /*删除歌手的信息*/
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

    /**
     * // TODO 这个后期需要分页
     * @return 所有的的歌手
     */
    @RequestMapping(value = "/allsinger", method = RequestMethod.GET)
    public List<Singer> allSinger(){
        List<Singer> singerList =  singerService.findAllSinger();
        return singerList;
    }

    /*根据歌手的名字查询歌手*/
    @RequestMapping(value = "/name/detail", method = RequestMethod.GET)
    public Object singerOfName(HttpServletRequest req){
        String name = req.getParameter("name").trim();
        List<Singer> singer = singerService.singerOfName(name);
        return singer;
    }

    /*根据歌手的性别查询歌手*/
    @RequestMapping(value = "/sex/detail", method = RequestMethod.GET)
    public List<Singer> singerOfSex(HttpServletRequest req){
        String sex = req.getParameter("sex").trim();
        List<Singer> singerSex = singerService.singerOfSex(Integer.parseInt(sex));
        return singerSex;
    }

    /**
     * 根据歌手的id返回属于他的歌曲的信息
     * @param request 查询的歌手的id
     * @return 返回这个数据
     */
    @GetMapping("/song/detail")
    public Object songOfSingerId(HttpServletRequest request) {
        String singerId = request.getParameter("singerId");
        if (singerId == null) {
            return AjaxResponse.setResult(ResultCodeEnum.SINGERID_NOT_NULL);
        }
        List<Song> songList = songService.selectSongBySingerId(Integer.parseInt(singerId));
        return songList;
    }







}
