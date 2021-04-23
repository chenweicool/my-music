package com.mymusic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mymusic.domain.UserComment;
import com.mymusic.common.domain.UserCommentVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserCommentMapper extends BaseMapper<UserComment> {

    /**
     * 分页读取用户的评论内容
     * @return 返回分页对象
     */
     IPage<UserCommentVo> getUserCommentByPage(Page<UserCommentVo> page);

    /**
     * 根据UUID唯一标识查询数据库。
     * @param commentUUID
     * @return
     */
     UserComment selectByUUID(String commentUUID);

    /**
     * 根据用户名返回用户的评论内容
     * @param userName 用户名
     * @return {@link UserCommentVo}
     */
    List<UserCommentVo> getCommentByUserName(String userName);

    /**
     * 根据多个歌曲id返回评论的内容
     * @param songIds 歌曲id
     * @return
     */
    IPage<UserCommentVo> getCommentBySongName(Page<UserCommentVo> page,@Param("songIds") List<Long> songIds);

    /**
     * 根据歌曲的id信息返回评论的内容
     * @param songId 歌曲的Id
     * @return 评论的内容
     */
    IPage<UserCommentVo> getCommentBySongId(Page<UserCommentVo> page,@Param("songId") Long songId);

    /**
     * 根据用户id，来查询用户的评论信息
     * @param page 当前页
     * @param userId 具体页数的大小
     * @return {@link IPage}
     */
    IPage<UserCommentVo> getUserCommentByUserId(Page<UserCommentVo> page, @Param("userId") Long userId);
}
