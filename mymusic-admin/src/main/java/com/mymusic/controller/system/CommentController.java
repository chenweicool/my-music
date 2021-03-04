package com.mymusic.controller.system;

import com.mymusic.common.exception.AjaxResponse;
import com.mymusic.domain.Comment;
import com.mymusic.service.impl.CommentServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Api(tags = "评论的接口")
@RestController
@Controller
public class CommentController {
   
    @Resource
    private CommentServiceImpl commentService;

  //  提交评论
    @ResponseBody
    @RequestMapping(value = "/comment/add", method = RequestMethod.POST)
    public AjaxResponse addComment(HttpServletRequest req){

        String user_id = req.getParameter("userId");
        String type = req.getParameter("type");
        String song_list_id=req.getParameter("songListId");
        String song_id=req.getParameter("songId");
        String content = req.getParameter("content").trim();

        Comment comment = new Comment();
        comment.setUserId(Integer.parseInt(user_id));
        comment.setType(Integer.parseInt(type));
        if (new Byte(type) == 0) {
            comment.setSongId(Integer.parseInt(song_id));
        } else if (new Byte(type) == 1) {
            comment.setSongListId(Integer.parseInt(song_list_id));
        }
        comment.setContent(content);
        comment.setCreateTime(new Date());
        boolean res = commentService.addComment(comment);
        if (res){
            return AjaxResponse.success("评论成功");
        }else {
            return AjaxResponse.error("评论失败");
        }

    }

   /*获取所有的歌曲列表*/
    @RequestMapping(value = "/comment", method = RequestMethod.GET)
    public List<Comment> allComment(){
        List<Comment> commentList = commentService.allComment();
        return commentList;
    }

    /*歌曲的id获取歌曲*/
    @RequestMapping(value = "/comment/song/detail", method = RequestMethod.GET)
    public Object commentOfSongId(HttpServletRequest req){
        String songId = req.getParameter("songId");
        return commentService.commentOfSongId(Integer.parseInt(songId));
    }

//    获得指定歌单ID的评论列表
    @RequestMapping(value = "/comment/songList/detail", method = RequestMethod.GET)
    public Object commentOfSongListId(HttpServletRequest req){
        String songListId = req.getParameter("songListId");
        return commentService.commentOfSongListId(Integer.parseInt(songListId));
    }


    /**
     * 提交点赞的接口信息
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/comment/like", method = RequestMethod.POST)
    public AjaxResponse commentOfLike(HttpServletRequest req){

    String id = req.getParameter("id").trim();
    String up = req.getParameter("up").trim();

    Comment comment = new Comment();
    comment.setId(Integer.parseInt(id));
    comment.setUp(Integer.parseInt(up));
    boolean res = commentService.updateCommentMsg(comment);
        if (res){
            return AjaxResponse.success("点赞成功");
        }else {
            return AjaxResponse.error("点赞失败");
        }
}

//    删除评论
    @RequestMapping(value = "/comment/delete", method = RequestMethod.GET)
    public AjaxResponse deleteComment(HttpServletRequest req){
        String id = req.getParameter("id");
        boolean res = commentService.deleteComment(Integer.parseInt(id));
        if (res){
            return AjaxResponse.success("删除成功");
        }else {
            return AjaxResponse.error("删除失败");
        }    }

//    更新评论
    @ResponseBody
    @RequestMapping(value = "/comment/update", method = RequestMethod.POST)
    public AjaxResponse updateCommentMsg(HttpServletRequest req){
        String id = req.getParameter("id").trim();
        String user_id = req.getParameter("userId").trim();
        String song_id = req.getParameter("songId").trim();
        String song_list_id = req.getParameter("songListId").trim();
        String content = req.getParameter("content").trim();
        String type = req.getParameter("type").trim();
        String up = req.getParameter("up").trim();

        Comment comment = new Comment();
        comment.setId(Integer.parseInt(id));
        comment.setUserId(Integer.parseInt(user_id));
        if (song_id == "") {
            comment.setSongId(null);
        } else {
            comment.setSongId(Integer.parseInt(song_id));
        }

        if (song_list_id == "") {
            comment.setSongListId(null);
        } else {
            comment.setSongListId(Integer.parseInt(song_list_id));
        }
        comment.setContent(content);
        comment.setType(Integer.parseInt(type));
        comment.setUp(Integer.parseInt(up));

        boolean res = commentService.updateCommentMsg(comment);
        if (res){
            return AjaxResponse.success("修改成功");
        }else {
            return AjaxResponse.error("修改失败");
        }
    }
}
