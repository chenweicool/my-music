package com.mymusic.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mymusic.common.domain.UserCommentVo;
import com.mymusic.common.exception.AjaxResponse;
import com.mymusic.domain.UserComment;
import com.mymusic.formvo.UserCommentSongRequest;
import com.mymusic.service.UserCommentService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;


@Api(tags = "评论的接口")
@RestController
@RequestMapping("/comment")
@Slf4j
public class UserCommentController {

    @Resource
    private UserCommentService userCommentService;

    @ResponseBody
    @PostMapping("/addComment")
    public AjaxResponse  addComment(@RequestBody UserCommentSongRequest userComment){
        return userCommentService.addComment(userComment);
    }

    @PostMapping("/updateComment")
    public AjaxResponse  updateComment(@RequestBody UserComment userComment){
        return userCommentService.updateComment(userComment);
    }

    @GetMapping("/deleteComment")
    public AjaxResponse deleteComment(@RequestParam("commentId") Long commentId) {
        Boolean result = userCommentService.deleteComment(commentId);
        if (result) {
            return AjaxResponse.success("删除成功");
        }else{
            return AjaxResponse.error("删除失败");
        }
    }

    /**
     * 分页查寻所有的评论的内容
     * @param pageNum 分页的数量
     * @param pageSize
     * @return
     */
    @GetMapping("/getCommentByPage")
    public AjaxResponse getCommentByPage(  @RequestParam(value = "pageNum" ,defaultValue = "1") Integer pageNum,
                                                  @RequestParam(value = "pageSize",defaultValue = "20") Integer pageSize){
        Page<UserCommentVo> page = new Page<>(pageNum, pageSize);
        IPage<UserCommentVo> page1 = userCommentService.getUserCommentByPage(page);
        return AjaxResponse.success(page1);
    }

    /**
     * 根据用户名查询用户的评论的信息
     * @param userName  用户名
     * @return {@link List}
     */
    @GetMapping("/getCommentByUserName")
    public AjaxResponse getCommentByUserName(@RequestParam("userName") String userName){
        List<UserCommentVo> userCommentVos = userCommentService.getCommentByUserName(userName);
        return  AjaxResponse.success(userCommentVos);
    }


    /**
     * 根据歌曲名查询该歌曲的评论内容
     * @param pageNum  页数中的数据
     * @param pageSize  分页的大小
     * @param songName 歌曲名
     * @return
     */
    @GetMapping("/getCommentBySongName")
    public AjaxResponse getCommentBySongName(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                                        @RequestParam(value = "pageSize",defaultValue = "20") Integer pageSize,
                                                        @RequestParam("songName") String songName) {
        Page<UserCommentVo> page = new Page<>(pageNum, pageSize);
        IPage<UserCommentVo> commentVoIPage = userCommentService.getCommentBySongName(page, songName);
        log.info("歌曲的总数是："+commentVoIPage.getTotal());
        return AjaxResponse.success(commentVoIPage);
    }

    /**
     * 根据用户的id来查询用户的评论信息
     * @param pageNum 分页的大小
     * @param pageSize 分页的具体数量
     * @param userId 用户id
     * @return {@link AjaxResponse}
     */
    @GetMapping("/getCommentByUserId")
    public AjaxResponse getCommentByUserId(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                           @RequestParam(value = "pageSize",defaultValue = "20") Integer pageSize,
                                           @RequestParam("userId") String userId) {
        Page<UserCommentVo> page = new Page<>(pageNum, pageSize);
        Long userIdDb = Long.parseLong(userId);
        IPage<UserCommentVo> page1 = userCommentService.getUserCommentByUserId(page,userIdDb);
        return AjaxResponse.success(page1);
    }

    /**
     * 根据歌曲的id查询
     * @param pageNum 当前页
     * @param pageSize 当前页数据量的大小
     * @param songId 歌曲id
     * @return
     */
    @GetMapping("/getCommentBySongId")
    public AjaxResponse getCommentBySongId(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                                   @RequestParam(value = "pageSize",defaultValue = "20") Integer pageSize,
                                                   @RequestParam("songId") Long songId){
        Page<UserCommentVo> page = new Page<>(pageNum, pageSize);
        IPage<UserCommentVo> commentBySongId = userCommentService.getCommentBySongId(page, songId);
        return AjaxResponse.success(commentBySongId);
    }


    @PostMapping("/updateCommentLikeNum")
    public AjaxResponse updateCommentLikeNum(@RequestParam("id") Long commentId, @RequestParam("likeNum") Integer likeNum) {
        return userCommentService.updateCommentLikeNum(commentId, likeNum);
    }

}
