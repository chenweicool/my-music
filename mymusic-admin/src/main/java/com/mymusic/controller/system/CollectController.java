package com.mymusic.controller.system;

import com.mymusic.common.enums.ResultCodeEnum;
import com.mymusic.common.exception.AjaxResponse;
import com.mymusic.common.exception.CustomExceptionType;
import com.mymusic.domain.Collect;
import com.mymusic.service.impl.CollectServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Api(tags = "用户收藏歌曲接口")
@RestController
@Controller
public class CollectController {

    @Autowired
    private CollectServiceImpl collectService;

   // 添加收藏的歌曲
    @ResponseBody
    @RequestMapping(value = "/collection/add", method = RequestMethod.POST)
    public AjaxResponse addCollection(HttpServletRequest req){

        String user_id = req.getParameter("userId");
        String type = req.getParameter("type");
        String song_id=req.getParameter("songId");
        String song_list_id=req.getParameter("songListId");
        if (song_id == ""){
            return AjaxResponse.setResult(ResultCodeEnum.SINGERID_NOT_NULL);
        } else if (collectService.existSongId(Integer.parseInt(user_id), Integer.parseInt(song_id))) {
            return AjaxResponse.setResult(ResultCodeEnum.NO_REPEAT);
        }
        Collect collect = new Collect();
        collect.setUserId(Integer.parseInt(user_id));
        collect.setType(Integer.parseInt(type));
        if (new Byte(type) == 0) {
            collect.setSongId(Integer.parseInt(song_id));
        } else if (new Byte(type) == 1) {
            collect.setSongListId(Integer.parseInt(song_list_id));
        }
        collect.setCreateTime(new Date());
        boolean res = collectService.addCollection(collect);
        if (res){
            return AjaxResponse.success("添加歌曲成功");
        }else {
            return AjaxResponse.error(CustomExceptionType.USER_INPUT_ERROR, "数据格式有问题");
        }
    }

//    返回所有用户收藏列表
    @RequestMapping(value = "/collection", method = RequestMethod.GET)
    public List<Collect> allCollection(){
        List<Collect> collectList = collectService.allCollect();
        return collectList;
    }

//    返回的指定用户ID收藏列表
    @RequestMapping(value = "/collection/detail", method = RequestMethod.GET)
    public List<Collect> collectionOfUser(HttpServletRequest req){
        String userId = req.getParameter("userId");
        List<Collect> collectList =  collectService.collectionOfUser(Integer.parseInt(userId));
        return collectList;
    }

//    删除收藏的歌曲
    @RequestMapping(value = "/collection/delete", method = RequestMethod.GET)
    public Object deleteCollection(HttpServletRequest req){
        String user_id = req.getParameter("userId").trim();
        String song_id = req.getParameter("songId").trim();
        boolean res = collectService.deleteCollect(Integer.parseInt(user_id), Integer.parseInt(song_id));
        if (res) {
            return AjaxResponse.success("删除成功");
        }else{
            return AjaxResponse.error(CustomExceptionType.SYSTEM_ERROR, "删除失败");
        }

    }

//    更新收藏
    @ResponseBody
    @RequestMapping(value = "/collection/update", method = RequestMethod.POST)
    public AjaxResponse updateCollectMsg(HttpServletRequest req){
          String id = req.getParameter("id").trim();
        String user_id = req.getParameter("userId").trim();
        String type = req.getParameter("type").trim();
        String song_id = req.getParameter("songId").trim();
//        String song_list_id = req.getParameter("songListId").trim();

        Collect collect = new Collect();
        collect.setId(Integer.parseInt(id));
        collect.setUserId(Integer.parseInt(user_id));
        collect.setType(Integer.parseInt(type));
        collect.setSongId(Integer.parseInt(song_id));

        boolean res = collectService.updateCollectMsg(collect);
        if (res) {
            return AjaxResponse.success("更新成功");
        }else{
            return AjaxResponse.error(CustomExceptionType.SYSTEM_ERROR, "更新失败");
        }
    }
}

