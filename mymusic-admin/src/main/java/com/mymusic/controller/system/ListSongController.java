package com.mymusic.controller.system;

import com.mymusic.common.exception.AjaxResponse;
import com.mymusic.domain.ListSong;
import com.mymusic.service.ListSongService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 歌单中具体的歌曲信息
 */
@Api(tags = "歌单歌曲的接口")
@RestController
@Slf4j
@RequestMapping("/listSong")
public class ListSongController {

    @Resource
    private ListSongService listSongService;

    /*在歌单中增加一条记录*/
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public AjaxResponse addListSong(HttpServletRequest req){
        String song_id = req.getParameter("songId").trim();
        String song_list_id = req.getParameter("songListId").trim();

        ListSong listsong = new ListSong();
        listsong.setSongId(Integer.parseInt(song_id));
        listsong.setSongListId(Integer.parseInt(song_list_id));

        boolean res = listSongService.addListSong(listsong);
        if (res) {
            return AjaxResponse.success("添加成功");
        } else {
            return AjaxResponse.error("添加歌单歌曲失败");
        }
    }

    //    更新歌单里面的歌曲信息
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public AjaxResponse updateListSongMsg(HttpServletRequest req){
        String id = req.getParameter("id").trim();
        String song_id = req.getParameter("songId").trim();
        String song_list_id = req.getParameter("songListId").trim();

        ListSong listsong = new ListSong();
        listsong.setId(Integer.parseInt(id));
        listsong.setSongId(Integer.parseInt(song_id));
        listsong.setSongListId(Integer.parseInt(song_list_id));

        boolean res = listSongService.updateListSongMsg(listsong);
        if (res) {
            return AjaxResponse.success("更新成功");
        } else {
            return  AjaxResponse.error("更新失败");
        }
    }

    /*删除歌单中具体的歌曲*/
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public AjaxResponse deleteListSong(HttpServletRequest req){
        String songId = req.getParameter("songId");
        boolean res = listSongService.deleteListSong(Integer.parseInt(songId));
        if (res) {
            return AjaxResponse.success("删除成功");
        } else {
            return  AjaxResponse.error("删出失败");
        }
    }

    /*根据歌单的id和歌曲的id删除歌单中的记录*/
    @RequestMapping(value = "/deleteBySongIdAndSongListId",method = RequestMethod.GET)
    public Object deleteBySongIdAndSongListId(HttpServletRequest request){
        String songId = request.getParameter("songId");
        String songListId  =  request.getParameter("songListId");
        boolean res = listSongService.deleteBySongIdAndSongListId(Integer.parseInt(songId), Integer.parseInt(songListId));
        if (res) {
            return AjaxResponse.success("删除成功");
        } else {
            return  AjaxResponse.error("删出失败");
        }
    }

    /*返回歌单中包含所有的歌曲*/
    @RequestMapping(value = "/listSong", method = RequestMethod.GET)
    public List<ListSong> allListSong(){
        List<ListSong> allSong = listSongService.allListSong();
        return allSong;
    }

    /*根据歌单id查询歌曲中的id信息*/
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public List<ListSong> listSongOfSongId(HttpServletRequest req){
        String songListId = req.getParameter("songListId");
        List<ListSong> allSong =  listSongService.listSongOfSongId(Integer.parseInt(songListId));
        return  allSong;
    }


}
