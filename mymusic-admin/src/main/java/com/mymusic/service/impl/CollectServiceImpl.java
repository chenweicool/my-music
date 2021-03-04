package com.mymusic.service.impl;

import com.mymusic.domain.Collect;
import com.mymusic.mapper.CollectMapper;
import com.mymusic.service.CollectService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class CollectServiceImpl implements CollectService {

    @Resource
    private CollectMapper collectMapper;

    /**
     * 增加一个收藏表
     * @param collect 收藏的实体类
     * @return 返回是否成功
     */
    @Override
    public boolean addCollection(Collect collect) {
        return collectMapper.insertSelective(collect) > 0 ;
    }

    /**
     * 判断歌曲是否别收藏
     * @param userId 用户的id
     * @param songId 歌曲的id的实现
     * @return 结果
     */
    @Override
    public boolean existSongId(Integer userId, Integer songId) {
        return collectMapper.existSongId(userId, songId)>0 ;
    }

    /**
     * 更新歌单中的信息
     * @param collect 收藏信息
     * @return
     */
    @Override
    public boolean updateCollectMsg(Collect collect) {
        return collectMapper.updateCollectMsg(collect) >0 ;
    }

    /**
     * 删除一个收藏表中的歌单信息
     * @param userId 用户的id
     * @param songId 歌曲的id
     * @return 结果信息
     */
    @Override
    public boolean deleteCollect(Integer userId, Integer songId) {
        return collectMapper.deleteCollect(userId, songId) >0 ;
    }

    /**
     * 返回所有的收藏歌单表
     * @return
     */
    @Override
    public List<Collect> allCollect()

    {
        return collectMapper.allCollect();
    }

    /**
     * 根据用户名返回收藏的歌单信息
     * @param userId 歌单的信息
     * @return 是否成功
     */
    @Override
    public List<Collect> collectionOfUser(Integer userId)

    {
        return collectMapper.collectionOfUser(userId);
    }
}
