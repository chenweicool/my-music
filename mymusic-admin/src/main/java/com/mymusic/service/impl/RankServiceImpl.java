package com.mymusic.service.impl;

import com.mymusic.domain.Rank;
import com.mymusic.mapper.RankMapper;
import com.mymusic.service.RankService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RankServiceImpl implements RankService {
    @Resource
    private RankMapper rankMapper;

    @Override
    public int rankOfSongListId(Long songListId) {
        int rankNum = rankMapper.selectRankNum(songListId);
        int SumScore = rankMapper.selectScoreSum(songListId);
        if(rankNum == 0){
            return 0;
        }else{
            return  SumScore/rankNum;
        }


    }

    @Override
    public boolean addRank(Rank rank) {
        return rankMapper.insertSelective(rank) > 0;
    }
}
