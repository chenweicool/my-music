package com.mymusic.service.impl;

import com.mymusic.domain.Comment;
import com.mymusic.mapper.CommentMapper;
import com.mymusic.service.CommentService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Override
    public boolean addComment(Comment comment) {
        return commentMapper.insertSelective(comment) > 0 ;
    }

    @Override
    public boolean updateCommentMsg(Comment comment) {
        return commentMapper.updateCommentMsg(comment) >0;
    }

    /**
     * 删除评论的信息
     * @param id 评论内容的id的值
     * @return
     */
    @Override
    public boolean deleteComment(Integer id) {
        return commentMapper.deleteComment(id) >0 ;
    }

    /**
     * 查询所有的评论信息
     * @return 评论的列表
     */
    @Override
    public List<Comment> allComment()
    {
        return commentMapper.allComment();
    }

    /**
     * 查询某一歌曲下的评论内容
     * @param songId 歌曲的id
     * @return
     */
    @Override
    public List<Comment> commentOfSongId(Integer songId)

    {
        return commentMapper.commentOfSongId(songId);
    }

    /**
     * 查询某个歌单中的评论信息
     * @param songListId
     * @return
     */
    @Override
    public List<Comment> commentOfSongListId(Integer songListId)

    {
        return commentMapper.commentOfSongListId(songListId);
    }
}
