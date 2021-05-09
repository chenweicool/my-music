package com.mymusic.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import com.mymusic.ConvertService;
import com.mymusic.common.domain.SysUserVO;
import com.mymusic.common.domain.UserCommentVo;
import com.mymusic.common.exception.AjaxResponse;
import com.mymusic.common.exception.UserException;
import com.mymusic.common.utils.ParameterCheckUtils;
import com.mymusic.domain.*;
import com.mymusic.formvo.UserCommentSongRequest;

import com.mymusic.mapper.UserCommentMapper;
import com.mymusic.service.UserCommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 评论的增删改查
 */
@Service
public class UserCommentServiceImpl implements UserCommentService {

    @Resource
    private UserCommentMapper  userCommentMapper;

    @Resource
    private ConvertService convertService;

    @Resource
    private SysUserServiceImpl sysUserService;

    @Resource
    private SongServiceImpl songService;

    /**
     * 已对评论的内容进行了过滤
     * @param request {@link UserCommentSongRequest}
     * @return
     */
    @Override
    public AjaxResponse addComment(UserCommentSongRequest request) {
        ParameterCheckUtils.checkParamIsBlank(request);
        ParameterCheckUtils.checkParamIsBlank(request.getCommentContent(),request.getUserName(),
                request.getUserId(),request.getSongId());

        UserComment userComment = convertService.convertUserComment(request);
        boolean isContainsSensitive = SensitiveWordBs.newInstance().contains(userComment.getContent());
        // 检测评论的内容是否包含敏感词
        if(isContainsSensitive){
            return AjaxResponse.success("评论中包含敏感词，请重新提交");
        }
        userComment.setCommentStatus(1);  // 审核通过
        userComment.setCreateTime(new Date());
        userComment.setUpdateTime(new Date());
        if (userComment.getSongId() != null) {
            userComment.setType(1);
        }
        String commentUUID = UUID.randomUUID().toString();
        userComment.setUuid(commentUUID);
        userComment.setLikeNum(0);  // 点赞数默认是0
        int result = userCommentMapper.insert(userComment);
        if (result > 0) {
            return AjaxResponse.success("评论成功");
        }else{
            return AjaxResponse.success("评论失败");
        }
    }

    /**
     * 更新的能力
     * @param userComment {@link  UserComment}
     * @return
     */
    @Override
    public AjaxResponse updateComment(UserComment userComment) {
        ParameterCheckUtils.checkParamIsBlank(userComment);
        ParameterCheckUtils.checkParamIsBlank(userComment.getContent(),userComment.getUserName());

        if (userComment.getUpdateTime() == null) {
            userComment.setUpdateTime(new Date());
        }
        if (userComment.getCreateTime() == null) {
            userComment.setCreateTime(new Date());
        }
        boolean isContainsSensitive = SensitiveWordBs.newInstance().contains(userComment.getContent());
        // 检测评论的内容是否包含敏感词
        if(isContainsSensitive){
            return AjaxResponse.success("评论中包含敏感词，请重新提交");
        }
        int result = userCommentMapper.updateById(userComment);
        if (result > 0) {
            return AjaxResponse.success("更新成功");
        }else{
            return AjaxResponse.success("更新失败");
        }
    }

    @Override
    public Boolean  deleteComment(Long id) {
        ParameterCheckUtils.checkParamIsBlank(id);
        return userCommentMapper.deleteById(id) > 0 ;
    }

    @Override
    public UserComment selectByCommentId(Long id) {
        if (id == null) {
            throw new RuntimeException("评论的id不能为空");
        }
        return userCommentMapper.selectById(id);
    }

    @Override
    public IPage<UserCommentVo> getUserCommentByPage(Page<UserCommentVo> page) {
        ParameterCheckUtils.checkParamIsBlank(page);
        IPage<UserCommentVo> userCommentByPage = userCommentMapper.getUserCommentByPage(page);
        return  userCommentByPage;
    }

    @Override
    public List<UserCommentVo> getCommentByUserName(String userName) {
        ParameterCheckUtils.checkParamIsBlank(userName);

        // 查询该用户是否存在
        SysUserVO userByUserName = sysUserService.getUserByUserName(userName);
        if (userByUserName == null) {
            throw new UserException("该用户不存在");
        }
        List<UserCommentVo> commentConsumerList = userCommentMapper.getCommentByUserName(userName);
        if (commentConsumerList.isEmpty()) {
            throw new UserException("没有该用户的评论信息");
        }

        return  commentConsumerList;
    }

    @Override
    public IPage<UserCommentVo> getCommentBySongName(Page<UserCommentVo> page,String songName) {
        ParameterCheckUtils.checkParamIsBlank(songName);
        List<Song> songList = songService.songOfName(songName);
        List<Long> songIds = new ArrayList<>();

        if (songList == null) {
            throw new RuntimeException("没有该歌曲");
        }

        for (Song song : songList) {
            Long songId = song.getId();
            songIds.add(songId);
        }
        IPage<UserCommentVo> ipage = userCommentMapper.getCommentBySongName(page,songIds);
        return ipage;
    }

    @Override
    public IPage<UserCommentVo> getCommentBySongId(Page<UserCommentVo> page,Long songId) {
        ParameterCheckUtils.checkParamIsBlank(songId);
        Song song = songService.selectSong(songId);
        if (song == null) {
            throw new RuntimeException("该歌曲的信息不存在");
        }

        IPage<UserCommentVo> ipage = userCommentMapper.getCommentBySongId(page,songId);
        return ipage;

    }


    @Override
    public IPage<UserCommentVo> getUserCommentByUserId(Page<UserCommentVo> page, Long userIdDb) {
        ParameterCheckUtils.checkParamIsBlank(userIdDb);
        return userCommentMapper.getUserCommentByUserId(page,userIdDb);
    }

    @Override
    public AjaxResponse updateCommentLikeNum(Long commentId, Integer likeNum) {
        ParameterCheckUtils.checkParamIsBlank(commentId,likeNum);
        int result = userCommentMapper.updateCommentLikeNum(commentId, likeNum);
        if (result > 0) {
            return AjaxResponse.success("点赞成功");
        }
        return null;
    }

}

