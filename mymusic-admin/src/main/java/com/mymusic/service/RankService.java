package com.mymusic.service;

import com.mymusic.domain.Rank;

public interface RankService {

    int rankOfSongListId(Long songListId);

    boolean addRank(Rank rank);
}
