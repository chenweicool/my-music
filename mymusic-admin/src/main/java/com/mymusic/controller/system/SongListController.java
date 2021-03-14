package com.mymusic.controller.system;

import com.mymusic.common.enums.ResultCodeEnum;
import com.mymusic.common.exception.AjaxResponse;
import com.mymusic.common.utils.Constants;
import com.mymusic.common.utils.FileUtils;
import com.mymusic.domain.Song;
import com.mymusic.domain.SongList;
import com.mymusic.service.SongListService;
import com.mymusic.service.SongService;
import io.swagger.annotations.Api;
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
    private SongService songService;

    @Value("${file.Access_Key}")
    private String Access_Key;

    @Value("${file.Secret_Key}")
    private String Secret_Key;

    /**
     * 添加歌单的信息
     * @param req 前台传入的数据信息
     * @return  歌单是否添加成功的信息
     */
    @PostMapping("/addSongList")
    public AjaxResponse addSongList(HttpServletRequest req){
        String title = req.getParameter("title").trim();
        String pic = req.getParameter("pic").trim();
        String introduction = req.getParameter("introduction").trim();
        String style = req.getParameter("style").trim();
        String userIdStr = req.getParameter("userId");

        SongList songList = new SongList();
        songList.setTitle(title);
        songList.setPic(pic);
        songList.setIntroduction(introduction);
        songList.setStyle(style);

        // TODO 这里写的不好，需要优化一下。
        if(userIdStr != null){
            Long userId = Long.parseLong(userIdStr);
            boolean res = songListService.addSongList(songList,userId);
            if (res){
                return AjaxResponse.success("添加成功");
            }else {
                return AjaxResponse.error("添加失败");
            }
        }else{
            boolean res = songListService.addSongList(songList);
            if (res){
                return AjaxResponse.success("添加成功");
            }else {
                return AjaxResponse.error("添加失败");
            }
        }
    }


    /*删除歌单的信息*/
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
    public AjaxResponse updateSongListMsg(HttpServletRequest req){
        String id = req.getParameter("id").trim();
        String title = req.getParameter("title").trim();
        String pic = req.getParameter("pic").trim();
        String introduction = req.getParameter("introduction").trim();
        String style = req.getParameter("style").trim();

        SongList songList = new SongList();
        songList.setId(Integer.parseInt(id));
        songList.setTitle(title);
        songList.setPic(pic);
        songList.setIntroduction(introduction);
        songList.setStyle(style);

        boolean res = songListService.updateSongListMsg(songList);
        if (res) {
            return AjaxResponse.success("更新成功");
        } else {
            return  AjaxResponse.error("更新失败");
        }
    }

    /*更新歌单的图片*/
    @ResponseBody
    @RequestMapping(value = "/img/update", method = RequestMethod.POST)
    public Map<String, Object> updateSongListPic(@RequestParam("file") MultipartFile picFile, @RequestParam("id")int id){

        HashMap<String, Object> res = new HashMap<>();
        if (picFile.isEmpty()) {
            res.put("文件夹不能为空", ResultCodeEnum.FILE_NOT_NULL);
            return res;
        }
        String songPic = Constants.SONGLIST_PIC + System.currentTimeMillis() + picFile.getOriginalFilename();
        String picPath = FileUtils.getAvatarPic(picFile, Access_Key, Secret_Key, songPic);

            SongList songList = new SongList();
            songList.setId(id);
            songList.setPic(picPath);
            boolean flag = songListService.updateSongListImg(songList);
        if (flag) {
            res.put("avator", picPath);
            return res;
        } else {
            res.put("error", ResultCodeEnum.UNKNOWN_ERROR);
            return res;
        }
    }

    /**
     * 返回所有的歌单信息
     * //TODO  后期也需要优化，肯定是后端进行分页的
     * @return
     */
    @GetMapping("/allSingList")
    public List<SongList> allSongList(){
        List<SongList> allSongList = songListService.allSongList();
        //return AjaxResponse.ok().data("allSongList", allSongList);
        return allSongList;
    }

    //  查询用户创建的歌单的信息
    @GetMapping("/mysonglist")
    public List<SongList> mySongList(HttpServletRequest request) {
       Long userId = Long.parseLong(request.getParameter("userId"));
        List<SongList> mySongList = songListService.findMySongList(userId);
        return  mySongList;
    }

    /*根据歌单的标题信息查找具体的歌单*/
    @RequestMapping(value = "/title/detail", method = RequestMethod.GET)
    public List<SongList> songListOfTitle(HttpServletRequest req){
        String title = req.getParameter("title").trim();
        List<SongList> allSongList = songListService.songListOfTitle(title);
        return allSongList;
    }

    /*返回指定标题的信息的歌单信息*/
    @RequestMapping(value = "/likeTitle/detail", method = RequestMethod.GET)
    public List<SongList> songListOfLikeTitle(HttpServletRequest req){
        String title = req.getParameter("title").trim();
        List<SongList> allSongList = songListService.likeTitle(title);
        return allSongList;
    }

    /*返回指定类型的歌单信息*/
    @RequestMapping(value = "/style/detail", method = RequestMethod.GET)
    public List<SongList> songListOfStyle(HttpServletRequest req){
        String style = req.getParameter("style").trim();
        List<SongList> allSongList = songListService.likeStyle(style);
        return  allSongList;
    }

    /**
     * 根据id查询单个歌单的信息
     * @param request 请求的方法
     * @return 歌单中的信息
     */
    @Deprecated
    @GetMapping("/songListInfo/detail")
    public AjaxResponse songListOfId(HttpServletRequest request){
        String songListId = request.getParameter("id");
        SongList songList = songListService.songListById(Integer.parseInt(songListId));
        return AjaxResponse.success(songList);
    }

    /**
     * 根据歌单中id返回每个歌单中歌曲
     * @param request 请求的信息。
     * @return
     */
    @RequestMapping(value = "/query/song",method = RequestMethod.GET)
    public List<Song>  findSongListSong(HttpServletRequest request){
         String songListId = request.getParameter("songListId");
        return songService.selectSongBySongListId(Integer.parseInt(songListId));
    }

}
