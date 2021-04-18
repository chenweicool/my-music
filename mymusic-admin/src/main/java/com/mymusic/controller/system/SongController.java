package com.mymusic.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mymusic.common.domain.SongVo;
import com.mymusic.common.enums.ResultCodeEnum;
import com.mymusic.common.exception.AjaxResponse;
import com.mymusic.common.utils.Constants;
import com.mymusic.common.utils.FileUtils;
import com.mymusic.domain.Song;
import com.mymusic.domain.SongList;
import com.mymusic.service.SongService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.loadtime.Aj;
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
     * //TODO 歌曲的上传逻辑这里需要实现一下
     * @param file 歌曲文件
     * @return 添加的结果
     */
    @ResponseBody
    @RequestMapping(value = "/addSong", method = RequestMethod.POST)
    public AjaxResponse addSong(@RequestBody Song song, @RequestParam(value = "file", required = false)
            MultipartFile file) {
        // todo 对歌曲名字进行正则处理
//        String singer_id = req.getParameter("singerId").trim();
//        String songName = req.getParameter("name").trim();
//        String introduction = req.getParameter("introduction").trim();
//        String lyric = req.getParameter("lyric").trim();
//        String pic = Constants.DEFAULT_PIC;    // 默认的图片的地址的信息
//        // 将歌曲文件上传至七牛云 todo 这里有问题
//        String SongKey = Constants.SONG_FILE + System.currentTimeMillis() + file.getOriginalFilename();  // 上传歌手文件的key的值
//        String songUrl = FileUtils.getAvatarPic(file, Access_Key, Secret_Key, SongKey);  // 返回歌曲的播放地址信息

        song.setCreateTime(new Date());
        song.setUpdateTime(new Date());
        song.setUrl("www.pingtu");
//        song.setPic(pic);
//        song.setUrl(songUrl);

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
     * @param
     * @return //TODo 需要优化
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public AjaxResponse updateSingerMsg(@RequestBody Song song) {

//       // JSONObject jsonObject = new JSONObject();
//        String id = req.getParameter("id").trim();
//        String singerId = req.getParameter("singerId").trim();
//        String name = req.getParameter("name").trim();
//        String introduction = req.getParameter("introduction").trim();
//        String lyric = req.getParameter("lyric").trim();
//
//        Song song = new Song();
//        song.setId(Long.parseLong(id));
//        song.setSingerId(Integer.parseInt(singerId));
//        song.setName(name);
//        song.setIntroduction(introduction);
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
    public AjaxResponse updateSongPic(@RequestParam("file") MultipartFile picFile, @RequestParam("id") Long id) {
        HashMap<String, Object> res = new HashMap<>();
        if (picFile.isEmpty()) {
            res.put("文件夹不能为空", ResultCodeEnum.FILE_NOT_NULL);
            return AjaxResponse.error("上传的文件不能为空");
        }

        String songPic = Constants.SONG_PIC + System.currentTimeMillis() + picFile.getOriginalFilename();
        String picPath = FileUtils.getAvatarPic(picFile, Access_Key, Secret_Key, songPic);

        Song song = new Song();
        song.setId(id);
        song.setPic(picPath);
        boolean flag = songService.updateSongPic(song);
        if (flag) {
            res.put("avator", picPath);
            return AjaxResponse.success(res);
        } else {
            return AjaxResponse.setResult(ResultCodeEnum.UNKNOWN_ERROR);
        }
    }

    /*删除歌曲的信息*/
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public AjaxResponse deleteSongBySongId(HttpServletRequest request) {
        String songId = request.getParameter("songId");
        boolean result = songService.deleteSong(Long.parseLong(songId));
        if (result) {
            return AjaxResponse.success("删除成功");
        } else {
            return  AjaxResponse.error("删除失败");
        }
    }

    /**
     * 歌曲信息的分页展示信息
     * @param pageNum 分页数量
     * @param pageSize   每页的大小
     * @return 具体的页数
     */
    @RequestMapping(value ="/querySongByPage")
    @ResponseBody
   public AjaxResponse querySong(
                                @RequestParam("pageNum") Integer pageNum,
                                @RequestParam("pageSize") Integer pageSize
                                ){
        IPage<SongVo> page = songService.selectSongByPage(pageNum, pageSize);
        if (page.getTotal() >= 1) {
            return AjaxResponse.success(page);
        }else{
            return AjaxResponse.success("暂无歌曲信息");
        }

   }

    /**
     * 根据歌曲名和歌手名返回歌曲的信息
     * @param pageNum 当前页
     * @param pageSize  分页的大小
     * @param queryName 查询的名字
     * @return
     */
    @RequestMapping(value = "/SongOfQuerySongNameOrSingerName", method = RequestMethod.GET)
    public  AjaxResponse querySongBySongNameOrSingerName( @RequestParam("pageNum") Integer pageNum,
                                                        @RequestParam("pageSize") Integer pageSize,
                                                        @RequestParam("queryName") String queryName) {
        if(StringUtils.isEmpty(queryName)){
            return AjaxResponse.success("参数不能为空");
        }
        IPage<SongVo> page = songService.querySongBySongNameOrSingerName(pageNum, pageSize, queryName);
         log.info("查询的总数是:"+page.getTotal());
        if(page.getTotal()!= 0){
            return  AjaxResponse.success(page);
        }else{
            AjaxResponse ajaxResponse = AjaxResponse.setResult(ResultCodeEnum.QUERY_NO_EXIST);
            log.info(ajaxResponse.toString());
            return AjaxResponse.success("查询的歌曲信息不存在，请重新查询");
        }
    }

    /**
     * 根据歌曲的id
     * @param request
     * @return
     */
    @GetMapping("/detail")
    public AjaxResponse songOfSongId(HttpServletRequest request) {
        String songId = request.getParameter("id");
        if (songId == null) {
            return AjaxResponse.setResult(ResultCodeEnum.SINGERID_NOT_NULL);
        }
        Song song = songService.selectSong(Long.parseLong(songId));
        return AjaxResponse.success(song);
    }


    /**
     * 根据歌手的id返回属于他的歌曲的信息
     * @return 返回这个数据
     */
    @GetMapping("/getSongOfSingerId")
    public AjaxResponse getSongOfSingerId(@RequestParam("pageNum") Integer pageNum,
                                       @RequestParam("pageSize") Integer pageSize,
                                       @RequestParam("singerId") String singerId) {
        if (singerId == null) {
            return AjaxResponse.setResult(ResultCodeEnum.SINGERID_NOT_NULL);
        }
        IPage<SongVo> songVoIpage = songService.selectSongBySingerId(pageNum,pageSize,Integer.parseInt(singerId));
        return AjaxResponse.success(songVoIpage);
    }

    /**
     * 根据歌单信息返回歌曲信息
     * @param pageNum 具体的页数大小
     * @param pageSize  分页的大小
     * @param songListId 歌单id的信息
     * @return
     */
    @GetMapping("/getSongOfSongListId")
    public AjaxResponse getSongOfSongListId(@RequestParam("pageNum") Integer pageNum,
                                            @RequestParam("pageSize") Integer pageSize,
                                            @RequestParam("songListId") String songListId){
        IPage<SongVo> page = songService.selectSongBySongListId(pageNum, pageSize, Integer.parseInt(songListId));
        if (page.getRecords().isEmpty()) {
            return AjaxResponse.success("没有对应的数据信息");
        }
        return AjaxResponse.success(page);
    }

}
