package com.mymusic.service.impl;

import com.mymusic.domain.UserReplyComment;
import com.mymusic.formvo.UserCommentVo;
import com.mymusic.service.UserReplyCommentService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

public class UserReplyCommentServiceImplTest extends BaseTest {

    @Resource
    private UserReplyCommentService replyCommentService;

    @Test
    public void addReplyComment() {
        UserReplyComment replyComment = new UserReplyComment();
        replyComment.setReplyUserId(137904599133525196L);
        replyComment.setUserId(1298090120930418690l);
        replyComment.setContent("讲的真好");
        replyComment.setUserName("yanfa1");
        Boolean aBoolean = replyCommentService.addReplyComment(replyComment);
        Assert.assertNotNull(aBoolean);
    }

    @Test
    @Transactional
    public void updateReplyComment() {
        UserReplyComment replyComment = replyCommentService.selectByReplyCommentId(1380871457617264642l);
        replyComment.setContent("我觉得又不好了");
        Boolean aBoolean = replyCommentService.updateReplyComment(replyComment);
        Assert.assertEquals(true, aBoolean);
    }

    @Test
    @Transactional
    public void deleteReplyComment() {
        Boolean aBoolean = replyCommentService.deleteReplyComment(1380872277477879809l);
        System.out.println(aBoolean);
    }

    @Test
    public void selectByReplyCommentId() {

    }

    @Test
    public void getByReplyCommentId() {
        List<UserCommentVo> userCommentVoList = replyCommentService.getByReplyCommentId(137904599133525196l);
        userCommentVoList.forEach(userCommentVo -> {
            System.out.println(userCommentVo.toString());
        });
    }
}