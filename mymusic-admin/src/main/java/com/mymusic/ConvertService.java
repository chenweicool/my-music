package com.mymusic;

import com.mymusic.common.domain.SysUserVO;
import com.mymusic.common.domain.UserCommentVo;
import com.mymusic.domain.SysUser;
import com.mymusic.domain.UserComment;
import com.mymusic.domain.UserCommentConsumer;
import com.mymusic.domain.UserReplyComment;
import com.mymusic.formvo.UserCommentSongRequest;
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
            userComment1.setContent(userComment.getCommentContent());
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

    public SysUserVO convertSysUserVo(SysUser sysUser) {
        SysUserVO sysUserVO = new SysUserVO();

        sysUserVO.setId(sysUser.getId());
        sysUserVO.setUsername(sysUser.getUsername());
        sysUserVO.setAvator(sysUser.getAvator());
        sysUserVO.setEnabled(sysUser.getEnabled());
        sysUserVO.setEmail(sysUser.getEmail());
        sysUserVO.setPhone(sysUser.getPhone());
        sysUserVO.setUpdateTime(sysUser.getUpdateTime());
        sysUserVO.setCreateTime(sysUserVO.getCreateTime());
        sysUserVO.setBirth(sysUser.getBirth());
        sysUserVO.setLocation(sysUser.getLocation());
        sysUserVO.setIntroduction(sysUser.getIntroduction());
        sysUserVO.setSex(sysUser.getSex());
        return sysUserVO;
    }
}
