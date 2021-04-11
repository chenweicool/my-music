package com.mymusic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mymusic.domain.UserReplyComment;

import java.util.List;

public interface UserReplyCommentMapper extends BaseMapper<UserReplyComment> {
    List<UserReplyComment>  getByReplyCommentId(Long ReplyUserId);
}
