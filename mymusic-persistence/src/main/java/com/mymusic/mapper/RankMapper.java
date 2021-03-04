package com.mymusic.mapper;

import com.mymusic.domain.Rank;

public interface RankMapper {
    int insert(Rank record);

    int insertSelective(Rank record);

    /**
     * 查总分
     * @param songListId 歌单的id的信息
     * @return 返回总的分数
     */
    int selectScoreSum(Long songListId);

    /**
     * 查总评分人数
     * @param songListId 总评分人数
     * @return 返回总的评分内容
     */
    int selectRankNum(Long songListId);
}
