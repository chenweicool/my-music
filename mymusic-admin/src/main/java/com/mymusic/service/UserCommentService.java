package com.mymusic.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mymusic.common.domain.UserCommentVo;
import com.mymusic.domain.UserComment;
import com.mymusic.formvo.UserCommentSongRequest;

import java.util.List;

/**
 * 用户的评论管理的实现类的信息
 */
public interface UserCommentService {

    /**
     * 添加一条评论
     * @param userComment {@link  UserComment}
     * @return
     */
    Boolean addComment(UserCommentSongRequest userComment);

    /**
     * 更新一条评论内容
     * @param userComment {@link  UserComment}
     * @return
     */
    Boolean updateComment(UserComment userComment);

    /**
     * 删除一条评论内容
     * @param id 评论的id
     * @return
     */
    Boolean deleteComment(Long id);

    /**
     * 根据的评论的id来查询评论
     * @param id 评论的id
     * @return
     */

    UserComment selectByCommentId(Long id);

    /**
     * 分页查询评论内容
     * @param page 具体分页对象
     * @return
     */
    IPage<UserCommentVo> getUserCommentByPage(Page<UserCommentVo> page);

    /**
     * 根据用户名查询用户的评论内容
     * @param userName  用户名
     * @return
     */
    List<UserCommentVo> getCommentByUserName(String userName);

    /**
     * 根据歌曲名或者歌手名查询所有的评论信息
     * @param songName 歌曲名
     * @return
     */
    IPage<UserCommentVo> getCommentBySongName(Page<UserCommentVo> page,String songName);

    /**
     * 根据歌曲的id查询所有的评论的内容
     * @param songId  歌曲的id的信息
     * @return {@link UserCommentVo}
     */
    IPage<UserCommentVo> getCommentBySongId(Page<UserCommentVo> page,Long  songId);

    /**
     * 根据用户名查询用户的信息
     * @param page 页数的大小
     * @param userIdDb 用户的id信息
     * @return {@link IPage}
     */
    IPage<UserCommentVo> getUserCommentByUserId(Page<UserCommentVo> page, Long userIdDb);
}
