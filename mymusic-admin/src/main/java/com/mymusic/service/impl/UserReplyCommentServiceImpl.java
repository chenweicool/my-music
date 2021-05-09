package com.mymusic.service.impl;

import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import com.mymusic.ConvertService;
import com.mymusic.common.domain.UserCommentVo;
import com.mymusic.common.exception.AjaxResponse;
import com.mymusic.common.utils.ParameterCheckUtils;
import com.mymusic.domain.UserReplyComment;
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
    public AjaxResponse addReplyComment(UserReplyComment replyComment) {
        ParameterCheckUtils.checkParamIsBlank(replyComment);
        ParameterCheckUtils.checkParamIsBlank(replyComment.getContent(),
                replyComment.getReplyUserId(),replyComment.getUserId());

        boolean containsSensitive = SensitiveWordBs.newInstance().contains(replyComment.getContent());
        if(containsSensitive){
            return AjaxResponse.success("评论中包含敏感词，请重新评论");
        }
        replyComment.setCreateTime(new Date());
        replyComment.setUpdateTime(new Date());
        int result = replyCommentMapper.insert(replyComment);
        if (result > 0) {
            return  AjaxResponse.success("评论成功");
        }else{
           return AjaxResponse.success("评论失败");
        }
    }

    @Override
    public AjaxResponse updateReplyComment(UserReplyComment replyComment) {
        ParameterCheckUtils.checkParamIsBlank(replyComment);
        ParameterCheckUtils.checkParamIsBlank(replyComment.getContent(),
                replyComment.getReplyUserId(),replyComment.getUserId());
        boolean containsSensitive = SensitiveWordBs.newInstance().contains(replyComment.getContent());
        if(containsSensitive){
            return AjaxResponse.success("评论中包含敏感词，请重新评论");
        }
        replyComment.setUpdateTime(new Date());
        int result = replyCommentMapper.updateById(replyComment);
        if (result > 0) {
            return  AjaxResponse.success("评论成功");
        }else{
            return AjaxResponse.success("评论失败");
        }
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
