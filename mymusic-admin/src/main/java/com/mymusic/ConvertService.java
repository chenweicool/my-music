package com.mymusic;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mymusic.domain.UserComment;
import com.mymusic.domain.UserCommentConsumer;
import com.mymusic.domain.UserReplyComment;
import com.mymusic.formvo.UserCommentSongRequest;
import com.mymusic.formvo.UserCommentVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConvertService {

    public UserCommentVo convertUserCommentVo(UserComment userComment) {
        UserCommentVo userCommentVo = new UserCommentVo();

        userCommentVo.setContent(userComment.getContent());
        userCommentVo.setUserName(userComment.getUserName());
        userCommentVo.setLikeNum(userComment.getLikeNum());
        userCommentVo.setAvatar(userComment.getAvatar());
        userCommentVo.setCreateTime(userComment.getCreateTime());
        return userCommentVo;
    }

    public UserCommentVo convertUserCommentVo(UserReplyComment userComment) {
        UserCommentVo userCommentVo = new UserCommentVo();
        userCommentVo.setContent(userComment.getContent());
        userCommentVo.setUserName(userComment.getUserName());
        userCommentVo.setLikeNum(userComment.getLikeNum());
        userCommentVo.setAvatar(userComment.getAvatar());
        userCommentVo.setCreateTime(userComment.getCreateTime());
        return userCommentVo;
    }

    public UserComment convertUserComment(UserCommentSongRequest userComment) {
        UserComment userComment1 = new UserComment();
            userComment1.setUserId(userComment.getUserId());
            userComment1.setUserName(userComment.getUserName());
            userComment1.setAvatar(userComment.getAvatar());
            userComment1.setContent(userComment.getContent());
            userComment1.setSongId(userComment.getSongId());
            return userComment1;
    }


    public UserCommentVo convertUserCommentVo(UserCommentConsumer record) {
        UserCommentVo userCommentVo = new UserCommentVo();

        userCommentVo.setId(record.getId());
        userCommentVo.setUserName(record.getUserName());
        userCommentVo.setSongName(record.getName());
        userCommentVo.setAvatar(record.getAvatar());
        userCommentVo.setLikeNum(record.getLikeNum());
        userCommentVo.setCreateTime(record.getCreateTime());
        userCommentVo.setContent(record.getContent());
        userCommentVo.setCommentStatus(record.getCommentStatus());
        return userCommentVo;
    }

    public List<UserCommentVo> convertUserCommentVOList(List<UserCommentConsumer> commentConsumerList) {
        List<UserCommentVo> commentVoList = new ArrayList<>();
        for (UserCommentConsumer userCommentConsumer : commentConsumerList) {
            UserCommentVo userCommentVo = convertUserCommentVo(userCommentConsumer);
            commentVoList.add(userCommentVo);
        }
        return commentVoList;
    }
}
