package com.mymusic.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mymusic.common.enums.ResultCodeEnum;
import com.mymusic.common.exception.AjaxResponse;
import com.mymusic.common.utils.Constants;
import com.mymusic.common.utils.FileUtils;
import com.mymusic.domain.Song;
import com.mymusic.domain.SongList;
import com.mymusic.service.SongService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.acl.LastOwnerException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 歌曲的控制类
 */
@Api(tags = "歌曲的api")
@RestController
@RequestMapping("/song")
@Slf4j
public class SongController {

    @Resource
    private SongService songService;

    @Value("${file.Access_Key}")
    private String Access_Key;

    @Value("${file.Secret_Key}")
    private String Secret_Key;

    /**
     * 添加歌曲
     * 这里的歌曲只是添加歌曲
     * 一般不需要这么做。
     * //TODO 需要判断一下重复的歌曲，不能重复的添加
     * @param file 歌曲文件
     * @return 添加的结果
     */
    @ResponseBody
    @RequestMapping(value = "/addSong", method = RequestMethod.POST)
    public AjaxResponse addSinger(HttpServletRequest req, @RequestParam(value = "file", required = false)
            MultipartFile file) {
        // 设置歌曲的信息
        String singer_id = req.getParameter("singerId").trim();
        String songName = req.getParameter("name").trim();
        String introduction = req.getParameter("introduction").trim();
        String lyric = req.getParameter("lyric").trim();
        String pic = Constants.DEFAULT_PIC;    // 默认的图片的地址的信息
        // 将歌曲文件上传至七牛云
        String SongKey = Constants.SONG_FILE + System.currentTimeMillis() + file.getOriginalFilename();  // 上传歌手文件的key的值
        String songUrl = FileUtils.getAvatarPic(file, Access_Key, Secret_Key, SongKey);  // 返回歌曲的播放地址信息
        Song song = new Song();
        /*完成歌手的信息的插入操作*/
        song.setCreateTime(new Date());
        song.setUpdateTime(new Date());
        song.setPic(pic);
        song.setUrl(songUrl);

        boolean res = songService.insertSong(song);

        if (res) {
            return AjaxResponse.success("添加成功");
        } else {
            return AjaxResponse.error("添加失败");
        }
    }

    /**
     * 更新歌曲的信息
     *
     * @param  req
     * @return //TODo 需要优化
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public AjaxResponse updateSingerMsg(HttpServletRequest req) {

       // JSONObject jsonObject = new JSONObject();
        String id = req.getParameter("id").trim();
        String singerId = req.getParameter("singerId").trim();
        String name = req.getParameter("name").trim();
        String introduction = req.getParameter("introduction").trim();
        String lyric = req.getParameter("lyric").trim();

        Song song = new Song();
        song.setId(Long.parseLong(id));
        song.setSingerId(Integer.parseInt(singerId));
        song.setName(name);
        song.setIntroduction(introduction);
        song.setUpdateTime(new Date());
       // song.setLyric(lyric);

        boolean res = songService.updateSong(song);
        if (res) {
            return AjaxResponse.success("更新成功");
        } else {
            return AjaxResponse.error("更新失败");
        }
    }

    /**
     * 更新歌曲的头像的信息
     *
     * @param picFile 歌曲的头像信息
     * @param id      歌曲的id
     * @return 是否更新成功的信息
     */
    @ResponseBody
    @RequestMapping(value = "/img/update", method = RequestMethod.POST)
    public HashMap<String,Object> updateSongPic(@RequestParam("file") MultipartFile picFile, @RequestParam("id") Long id) {
        HashMap<String, Object> res = new HashMap<>();
        if (picFile.isEmpty()) {
            res.put("文件夹不能为空", ResultCodeEnum.FILE_NOT_NULL);
            return res;
        }

        String songPic = Constants.SONG_PIC + System.currentTimeMillis() + picFile.getOriginalFilename();
        String picPath = FileUtils.getAvatarPic(picFile, Access_Key, Secret_Key, songPic);

        Song song = new Song();
        song.setId(id);
        song.setPic(picPath);
        boolean flag = songService.updateSongPic(song);
        if (flag) {
            res.put("avator", picPath);
            return res;
        } else {
            res.put("error", ResultCodeEnum.UNKNOWN_ERROR);
            return res;
        }
    }

    /*删除歌曲的信息*/
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public AjaxResponse deleteSinger(HttpServletRequest request) {
        String songId = request.getParameter("songId");
        boolean result = songService.deleteSong(Long.parseLong(songId));
        if (result) {
            return AjaxResponse.success("删除成功");
        } else {
            return  AjaxResponse.error("删除失败");
        }
    }


    /**
     * 这个方法后期应该是不行的，如果歌曲太多的话，系统就会崩溃
     * 所以，必须要分页
     * // TODO 这个方法
     * @return
     */
    @Deprecated
    @RequestMapping(value = "/allsongs", method = RequestMethod.GET)
    public List<Song> allSong() {
        List<Song> songList = songService.selectAll();
        return songList;
    }

    /**
     * 歌曲信息的分页展示信息
     * @param name 歌曲名
     * @param introduction 歌曲介绍
     * @param lyric 歌词的信息
     * @param pageNum 分页数量
     * @param pageSize   每页的大小
     * @return 具体的页数
     */
    @RequestMapping(value = "/querysong",method = RequestMethod.POST)
   public IPage<Song> querySong(@RequestParam("name")String name,
                                @RequestParam("introduction")String introduction,
                                @RequestParam("lyric")String lyric,
                                @RequestParam("pageNum") Integer pageNum,
                                @RequestParam("pageSize") Integer pageSize
                                ){

          return songService.querySong(name,introduction,lyric,pageNum,pageSize);
   }
    /**
     * 根据歌曲的名字返回歌曲的信息
     * //TODO 需要异常处理,将异常处理的消息返回给前端
     * @param req 前端传来的参数
     * @return  返回结果
     */
    @RequestMapping(value = "/SongOfSingerName", method = RequestMethod.GET)
    public  List<Song> songOfName(HttpServletRequest req) {
        String name = req.getParameter("songName").trim();
        List<Song> songList = songService.songOfName(name);
        if (songList == null) {
            throw new RuntimeException("查询的歌曲不存在");
        }
        return songList;
    }

    /**
     * 根据歌手的id返回属于他的歌曲的信息
     *
     * @param request 查询的歌手的id
     * @return 返回这个数据
     */
    @Deprecated
    @GetMapping("/singer/detail")
    public Object songOfSingerId(HttpServletRequest request) {
        String singerId = request.getParameter("singerId");
        if (singerId == null) {
            return AjaxResponse.setResult(ResultCodeEnum.SINGERID_NOT_NULL);
        }
        List<Song> songList = songService.selectSongBySingerId(Integer.parseInt(singerId));
        return songList;
    }

    /*根据歌曲的id 返回歌曲*/
    @GetMapping("/detail")
    public AjaxResponse songOfSongId(HttpServletRequest request) {
        String songId = request.getParameter("id");
        if (songId == null) {
            return AjaxResponse.setResult(ResultCodeEnum.SINGERID_NOT_NULL);
        }
        Song song = songService.selectSong(Long.parseLong(songId));
        return AjaxResponse.success(song);
    }
}
