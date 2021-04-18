package com.mymusic.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mymusic.ConvertService;
import com.mymusic.common.domain.UserCommentVo;
import com.mymusic.domain.UserComment;
import com.mymusic.domain.UserCommentConsumer;
import com.mymusic.formvo.UserCommentSongRequest;
import com.mymusic.service.UserCommentService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.List;

public class UserCommentServiceImplTest extends BaseTest {

    @Resource
    private UserCommentService userCommentService;

    @Resource
    private ConvertService convertService;
    Page<UserCommentVo> iPage ;

    @Test
    public void addComment() {
        UserCommentSongRequest userComment = new UserCommentSongRequest();
        userComment.setUserId(1297873308628307970l);
        userComment.setUserName("admin");
        userComment.setContent("这首歌真的难听");
        userComment.setSongId(12l);
       // userComment.setCommentStatus(1);
        Boolean aBoolean = userCommentService.addComment(userComment);
        Assert.assertEquals(true, aBoolean);

    }

    /**
     * 测试评论的内容的查询
     */
    @Test
    public void testById(){
        UserComment userComment = userCommentService.selectByCommentId(1380828400825323521l);
        System.out.println(userComment.toString());
        Assert.assertNotNull(userComment);

    }
    @Test
    @Transactional
    public void updateComment() {
        UserComment userComment = userCommentService.selectByCommentId(1380828400825323521l);
        userComment.setContent("就是这样的歌曲信息");
        Boolean aBoolean = userCommentService.updateComment(userComment);
        Assert.assertEquals(true, aBoolean);
    }

    @Test
    @Transactional
    public void deleteComment() {
        Boolean result = userCommentService.deleteComment(1380828400825323521l);
        System.out.println(result);
    }

    /**
     * 测试分页的信息
     */
    @Test
    public void getUserCommentByPage() {
        iPage = new Page<>(1, 10);
        IPage<UserCommentVo> userCommentByPage = userCommentService.getUserCommentByPage(iPage);
        List<UserCommentVo> records = userCommentByPage.getRecords();
        for (UserCommentVo record : records) {
            System.out.println(record);
        }
        Assert.assertNotNull(userCommentByPage);
    }

    /**
     * 测试根据歌曲名查询评论
     * 返回的内容过多。
     */
    @Test
    public void getCommentBySongName(){
        iPage = new Page<>(1, 4);
        IPage<UserCommentVo> commentBySongName = userCommentService.getCommentBySongName(iPage,"张杰-如果爱");
        List<UserCommentVo> records = commentBySongName.getRecords();
        for (UserCommentVo record : records) {
            System.out.println(record);
        }
        Assert.assertNotNull(commentBySongName);
    }

    /**
     * 根据歌曲的id查询评论的信息
     */
    @Test
    public void getCommentBySongId(){
        iPage = new Page<>(1, 3);
        IPage<UserCommentVo> commentBySongName = userCommentService.getCommentBySongId(iPage,4l);
        List<UserCommentVo> records = commentBySongName.getRecords();
        for (UserCommentVo record : records) {
            System.out.println(record);
        }
        Assert.assertNotNull(commentBySongName);
    }

    // 根据用户名查询用户评论信息
    @Test
    public void getCommentByUserName(){
        List<UserCommentVo> records = userCommentService.getCommentByUserName("admin");
        for (UserCommentVo record : records) {
            System.out.println(record);
        }
        Assert.assertNotNull(records);
    }

}