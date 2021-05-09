package com.mymusic.service;

import com.mymusic.common.domain.UserCommentVo;
import com.mymusic.common.exception.AjaxResponse;
import com.mymusic.domain.UserComment;
import com.mymusic.domain.UserReplyComment;

import java.util.List;

public interface  UserReplyCommentService {
    /**
     * 添加一条回复
     * @param  replyComment {@link  UserReplyComment}
     * @return
     */
    AjaxResponse addReplyComment(UserReplyComment replyComment);

    /**
     * 更新一条评论内容
     * @param replyComment {@link  UserReplyComment}
     * @return
     */
    AjaxResponse updateReplyComment(UserReplyComment replyComment);

    /**
     * 删除一条回复
     * @param id {@link UserComment}
     * @return
     */
    Boolean deleteReplyComment(Long id);

    /**
     * 根据的评论的id来查询评论
     * @param id 评论的id
     * @return
     */
    UserReplyComment selectByReplyCommentId(Long id);

    /**
     * 根据被回复者的id 来查此条评论共有多少评论
     *
     */
     List<UserCommentVo> getByReplyCommentId(Long ReplyUserId);
}
