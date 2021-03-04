package com.mymusic.mapper;

import com.mymusic.domain.Comment;

import java.util.List;

/**
 * 评论内容的Mapper类的实现
 */
public interface CommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    int updateCommentMsg(Comment record);

    int deleteComment(Integer id);

    List<Comment> allComment();

    List<Comment> commentOfSongId(Integer songId);

    List<Comment> commentOfSongListId(Integer songListId);
}
