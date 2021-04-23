package com.mymusic.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mymusic.common.domain.SongListCollectVo;
import com.mymusic.domain.Collect;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*用户收藏表mapper层的实现*/
public interface CollectMapper {

    int  addCollectionSongList(Collect collect);

    int  deleteCollectBySongListId(@Param("collectSongListId") Integer collectSongListId);

    IPage<SongListCollectVo> getCollectsByUserId(@Param("page") IPage<SongListCollectVo> page, @Param("userIdDB") Long userIdDB);

    Integer getCount(@Param("songListCollectId") Integer songListCollectId);
}
