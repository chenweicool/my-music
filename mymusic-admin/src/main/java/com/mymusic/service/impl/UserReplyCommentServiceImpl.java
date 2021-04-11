package com.mymusic.service.impl;

import com.mymusic.ConvertService;
import com.mymusic.common.utils.ParameterCheckUtils;
import com.mymusic.domain.UserReplyComment;
import com.mymusic.formvo.UserCommentVo;
import com.mymusic.mapper.UserReplyCommentMapper;
import com.mymusic.service.UserReplyCommentService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserReplyCommentServiceImpl implements UserReplyCommentService {
    @Resource
    private UserReplyCommentMapper replyCommentMapper;

    @Resource
    private ConvertService convertService;

    @Override
    public Boolean addReplyComment(UserReplyComment replyComment) {
        ParameterCheckUtils.checkParamIsBlank(replyComment);
        ParameterCheckUtils.checkParamIsBlank(replyComment.getContent(),
                replyComment.getReplyUserId(),replyComment.getUserId());
        if (replyComment.getCreateTime() == null) {
            replyComment.setCreateTime(new Date());
        }
        if (replyComment.getUpdateTime() == null) {
            replyComment.setUpdateTime(new Date());
        }


        return replyCommentMapper.insert(replyComment)> 0;
    }

    @Override
    public Boolean updateReplyComment(UserReplyComment replyComment) {
        ParameterCheckUtils.checkParamIsBlank(replyComment);
        ParameterCheckUtils.checkParamIsBlank(replyComment.getContent(),
                replyComment.getReplyUserId(),replyComment.getUserId());
        replyComment.setUpdateTime(new Date());

        return replyCommentMapper.updateById(replyComment) >0;
    }

    @Override
    public Boolean deleteReplyComment(Long id) {
        ParameterCheckUtils.checkParamIsBlank(id);
        return replyCommentMapper.deleteById(id) > 0;
    }

    @Override
    public UserReplyComment selectByReplyCommentId(Long id) {
        ParameterCheckUtils.checkParamIsBlank(id);
        return replyCommentMapper.selectById(id);
    }

    @Override
    public List<UserCommentVo> getByReplyCommentId(Long replyUserId) {
        ParameterCheckUtils.checkParamIsBlank(replyUserId);
        List<UserCommentVo> userCommentVoList = new ArrayList<>();
        List<UserReplyComment> list = replyCommentMapper.getByReplyCommentId(replyUserId);

        for (UserReplyComment userReplyComment : list) {
            UserCommentVo userCommentVo = convertService.convertUserCommentVo(userReplyComment);
            userCommentVoList.add(userCommentVo);
        }
        return userCommentVoList;
    }

}
