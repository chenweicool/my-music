package com.mymusic.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mymusic.common.enums.ResultCodeEnum;
import com.mymusic.common.exception.AjaxResponse;
import com.mymusic.common.utils.Constants;
import com.mymusic.common.utils.FileUtils;
import com.mymusic.domain.Song;
import com.mymusic.domain.SongList;
import com.mymusic.service.SongListService;
import com.mymusic.service.SongService;
import com.mymusic.service.UpdatePictureOrFileService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 歌单信息
 */
@Api(tags = "歌单接口")
@RestController
@RequestMapping("/songList")
public class SongListController {

    @Resource
    private SongListService songListService;

    @Resource
    private UpdatePictureOrFileService pictureOrFileService;

    /**
     * 添加歌单的信息
     * @param songList
     * @return  歌单是否添加成功的信息
     */
    @PostMapping("/addSongList")
    public AjaxResponse addSongList(@RequestBody SongList songList){
            boolean res = songListService.addSongList(songList);
            if (res){
                return AjaxResponse.success("添加成功");
            }else {
                return AjaxResponse.error("添加失败");
            }
    }


    /**
     * 删除歌单需要判断一下，该歌单中歌曲是否存在
     * @param req
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public AjaxResponse deleteSongList(HttpServletRequest req){
        String id = req.getParameter("id");
        //Long userId = Long.parseLong(req.getParameter("userId"));
        boolean res = songListService.deleteSongList(Integer.parseInt(id));
        if (res) {
            return AjaxResponse.success("删除成功");
        } else {
            return  AjaxResponse.error("删出失败");
        }
    }

    /*更新歌单的信息*/
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public AjaxResponse updateSongListMsg(@RequestBody SongList songList){

        boolean res = songListService.updateSongListMsg(songList);
        if (res) {
            return AjaxResponse.success("更新成功");
        } else {
            return  AjaxResponse.error("更新失败");
        }
    }

    /**
     * 更新歌歌单的海报信息
     * @return 返回歌曲的海报信息
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
            SongList songList = songListService.songListById(id);
            songList.setPic(fileKey);
            boolean updateResult = songListService.updateSongListMsg(songList);
            if(updateResult){
                return AjaxResponse.success("更新图片信息成功");
            }else{
                return AjaxResponse.error("更新图片失败");
            }
        }else{
            return AjaxResponse.error("上传图片失败");
        }
    }

    // =======================================查询的接口
    /**
     * 根据id查询单个歌单的信息
     * @param songListId 歌单中id
     * @return 歌单中的信息
     */
    @Deprecated
    @GetMapping("/songListInfoById")
    public AjaxResponse songListOfId(@RequestParam("songListId")String songListId){
        SongList songList = songListService.songListById(Integer.parseInt(songListId));
        return AjaxResponse.success(songList);
    }


    /**
     * 分页查询歌单的信息
     * @return
     */
    @GetMapping("/getSongListByPage")
    public AjaxResponse getSongListByPage(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize) {
        IPage<SongList> iPage = songListService.getSongListByPage(pageNum,pageSize);
        return  AjaxResponse.success(iPage) ;
    }
    /**
     * 查询用户自己创建的歌单信息
     * @return
     */
    @GetMapping("/getMySongList")
    public AjaxResponse getMySongList(@RequestParam("pageNum") Integer pageNum,
                                        @RequestParam("pageSize") Integer pageSize,
                                        @RequestParam("userId") String userId) {
        Long userIdDb = Long.parseLong(userId);
        IPage<SongList> iPage = songListService.findMySongList(pageNum,pageSize,userIdDb);
        return  AjaxResponse.success(iPage) ;
    }

    /*根据歌单的标题信息查找具体的歌单*/
    @RequestMapping(value = "/getTitleByPage", method = RequestMethod.GET)
    public AjaxResponse songListOfTitle(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                        @RequestParam(value = "pageSize",defaultValue = "20") Integer pageSize,
                                        @RequestParam("title") String title){
        IPage<SongList> iPage = songListService.songListOfTitle(pageNum,pageSize,title);
        if (iPage.getSize() < 1) {
            return AjaxResponse.success("查询的信息不存在，请重新输入查询");
        }
        return AjaxResponse.success(iPage);
    }

    /*返回指定类型的歌单信息*/
    @RequestMapping(value = "/getStyleByPage", method = RequestMethod.GET)
    public AjaxResponse songListOfStyle(@RequestParam("pageNum") Integer pageNum,
                                          @RequestParam("pageSize") Integer pageSize,
                                          @RequestParam("style") String style){
        IPage<SongList> iPage  = songListService.likeStyle(pageNum,pageSize,style);
        return AjaxResponse.success(iPage);
    }

    /**
     * 这个获取热门的10个歌单信息
     * @return
     */
    @RequestMapping(value = "/getSongListHot",method = RequestMethod.GET)
    public AjaxResponse songListByHot(){
        List<SongList> songLists =  songListService.getSongListHot();
        return AjaxResponse.success(songLists);
    }

    /**
     * 移除歌曲收藏
     * @param songId 歌曲id
     * @param songListId 歌单id
     * @param type 歌单类型
     * @return
     */
    @RequestMapping("/deleteSongToSongList")
    public AjaxResponse deleteSongToSongList(@RequestParam("songId") Long songId,@RequestParam("songListId") Integer songListId,@RequestParam("type")Integer type){
        return songListService.deleteSongToSongList(songId, songListId,type);
    }

    /**
     * 添加歌曲到歌单
     * @param songId 歌曲id
     * @param songListIds 歌单Id
     * @param type 歌单类型
     * @return
     */
    @RequestMapping("/addSongToSongList")
    public AjaxResponse addSongToSongList(@RequestParam("songId") Long songId,@RequestParam("songListIds") List<Integer> songListIds,@RequestParam("type")Integer type){
        return songListService.addSongToSongList(songId, songListIds,type);
    }

}
