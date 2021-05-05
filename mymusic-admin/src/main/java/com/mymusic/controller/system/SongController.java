package com.mymusic.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mymusic.common.domain.SongVo;
import com.mymusic.common.enums.ResultCodeEnum;
import com.mymusic.common.exception.AjaxResponse;
import com.mymusic.common.utils.Constants;
import com.mymusic.common.utils.FileUtils;
import com.mymusic.domain.Singer;
import com.mymusic.domain.Song;
import com.mymusic.domain.SongList;
import com.mymusic.service.SongService;
import com.mymusic.service.UpdatePictureOrFileService;
import io.swagger.annotations.Api;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.MutablePropertyValues;
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

    @Resource
    private UpdatePictureOrFileService pictureOrFileService;

    /**
     * 添加歌曲
     * 这里的歌曲只是添加歌曲,同时还要添加歌曲的文件
     * //TODO 歌曲的上传逻辑这里需要实现一下
     * @return 添加的结果
     */
   // @ResponseBody
    @RequestMapping(value = "/addSong", method = RequestMethod.POST)
    public AjaxResponse addSong(@RequestBody Song song) {
//        Song song = new Song();
//        song.setIntroduction(introduction);
//        song.setLyric(lyric);
//        song.setName(name);
        boolean res = songService.insertSong(song);
        if (res) {
            return AjaxResponse.success("添加歌曲成功");
        } else {
            return AjaxResponse.error("添加歌曲失败");
        }
    }

    /**
     * 更新歌曲的信息
     * @return 返回歌曲的海报信息
     */
    @PostMapping("/updateSongFile")
    public AjaxResponse updateSongFile(@RequestParam("file") MultipartFile songFile, @RequestParam("id") Long id){
        if (songFile.isEmpty()) {
            return AjaxResponse.error("上传的文件信息不能为空");
        }
        String fileName = System.currentTimeMillis() + songFile.getOriginalFilename();
        String fileKey = Constants.SONG_FILE+fileName;
        String result = pictureOrFileService.updatePictureOrFile(songFile, fileKey);
        if(!StringUtils.isEmpty(result)){
            Song song  = songService.selectSong(id);
            song.setUrl(fileKey);
            boolean updateResult = songService.updateSong(song);
            if(updateResult){
                return AjaxResponse.success("更新歌曲文件成功");
            }else{
                return AjaxResponse.error("更新歌曲文件失败");
            }
        }else{
            return AjaxResponse.error("歌曲更新失败");
        }
    }

    /**
     * 更新歌手的信息
     * @param song {@link Song}
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public AjaxResponse updateSingerMsg(@RequestBody Song song) {
        boolean res = songService.updateSong(song);
        if (res) {
            return AjaxResponse.success("更新成功");
        } else {
            return AjaxResponse.error("更新失败");
        }
    }

    /**
     * 更新歌曲的海报信息
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
            Song song  = songService.selectSong(id);
            song.setPic(fileKey);
            boolean updateResult = songService.updateSong(song);
            if(updateResult){
                return AjaxResponse.success("更新图片信息成功");
            }else{
                return AjaxResponse.error("更新图片失败");
            }
        }else{
            return AjaxResponse.error("上传图片失败");
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
    public  AjaxResponse querySongBySongNameOrSingerName( @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                                        @RequestParam(value = "pageSize",defaultValue = "20") Integer pageSize,
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

    /**
     * 返回热门歌曲
     */
    @GetMapping("/getHotSong")
    public  AjaxResponse getHotSong(){
         return songService.getHotSong();
    }

    @PostMapping("/getRecommendSong")
    public AjaxResponse getRecommendSong(@RequestParam("userId") Long userId) {
        return songService.getRecommendSong(userId);
    }
}
