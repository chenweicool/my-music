package com.mymusic.service;

import com.mymusic.domain.Collect;

import java.util.List;

/*
* 用户的歌单收集类的实现
* */
public interface CollectService {

    boolean addCollection(Collect collect);

    boolean existSongId(Integer userId, Integer songId);

    boolean updateCollectMsg(Collect collect);

    boolean deleteCollect(Integer userId, Integer songId);

    List<Collect> allCollect();

    List<Collect> collectionOfUser(Integer userId);
}
