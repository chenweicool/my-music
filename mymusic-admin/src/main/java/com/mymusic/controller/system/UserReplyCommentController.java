package com.mymusic.controller.system;

import com.mymusic.common.domain.UserCommentVo;
import com.mymusic.common.exception.AjaxResponse;
import com.mymusic.domain.UserReplyComment;
import com.mymusic.service.UserReplyCommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/replyComment")
public class UserReplyCommentController {

    @Resource
    private UserReplyCommentService replyCommentService;

    @ResponseBody
    @PostMapping("/addReplyComment")
    public AjaxResponse addReplyComment(@RequestBody UserReplyComment userComment){
        Boolean result = replyCommentService.addReplyComment(userComment);
        if (result) {
            return AjaxResponse.success("添加评论成功");
        }else{
            return AjaxResponse.error("添加评论失败");
        }
    }

    @PostMapping("/updateReplyComment")
    public AjaxResponse  updateReplyComment(@RequestBody UserReplyComment userComment){
        Boolean result = replyCommentService.updateReplyComment(userComment);
        if (result) {
            return AjaxResponse.success();
        }else{
            return AjaxResponse.error("更新评论失败");
        }
    }

    // todo 删除需要判断一下
    @GetMapping("/deleteReplyComment")
    public AjaxResponse deleteComment(@RequestParam("commentId") Long commentId) {
        Boolean result = replyCommentService.deleteReplyComment(commentId);
        if (result) {
            return AjaxResponse.success();
        }else{
            return AjaxResponse.error("删除失败");
        }
    }

    /**
     * 查询一个评论下的所有回复
     * @param replyUserId 评论人的id
     * @return
     */
    @GetMapping("/getCommentByReplyId")
    public List<UserCommentVo> getCommentByReplyId(@RequestParam("replyUserId") Long replyUserId){
        return replyCommentService.getByReplyCommentId(replyUserId);
    }

}
