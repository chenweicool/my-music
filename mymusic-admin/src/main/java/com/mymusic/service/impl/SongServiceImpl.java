package com.mymusic.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mymusic.common.config.CosProperties;
import com.mymusic.common.domain.SongVo;
import com.mymusic.common.enums.SongConsumerType;
import com.mymusic.common.exception.AjaxResponse;
import com.mymusic.common.exception.CustomException;
import com.mymusic.common.exception.SongException;
import com.mymusic.common.utils.Constants;
import com.mymusic.common.utils.FileUtils;
import com.mymusic.domain.Song;
import com.mymusic.mapper.SongMapper;
import com.mymusic.service.ListSongService;
import com.mymusic.service.SongService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sun.java2d.pipe.AAShapePipe;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.List;

@Service
public class SongServiceImpl implements SongService
{
    @Resource
    private SongMapper songMapper;
    @Resource
    private CosProperties cosProperties;

    @Override
    public boolean deleteSong(Long id) {
        Song song = songMapper.selectByPrimaryKey(id);
        if (song == null) {
            throw new SongException(SongConsumerType.SONG_NOT_EXIST.getMessage());
        }
        return songMapper.deleteByPrimaryKey(id) > 0;
    }

    /**
     * 插入歌曲的实现
     * todo  这里需要实现歌手的插入
     * @param record {@link Song}
     * @return
     */
    @Transactional
    @Override
    public boolean insertSong(Song record) {
        record.setUpdateTime(new Date());
        record.setCreateTime(new Date());
//        String fileName = System.currentTimeMillis() + file.getOriginalFilename();
//        String fileKey = Constants.SONG_FILE+fileName;
//        String uploadResult = FileUtils.upLoadPicture(file, cosProperties, fileKey);
//        if(StringUtils.isEmpty(uploadResult)){
//            throw new RuntimeException("上传歌曲信息失败");
//        }else{
          //  record.setUrl(fileKey);
            record.setPic(Constants.DEFAULT_PIC);
            record.setUrl(Constants.DEFAULT_SONG_FILE);
            return songMapper.insert(record) > 0;
       // }
    }

    @Override
    public Song selectSong(Long  id) {
        Song song = songMapper.selectByPrimaryKey(id);
        if (song == null) {
            throw new SongException(SongConsumerType.SONG_NOT_EXIST.getMessage());
        }
        return song;
    }

    /**
     * 更新歌曲
     * @param record
     * @return
     */
    @Override
    public boolean updateSong(Song record) {
        record.setUpdateTime(new Date());
        return songMapper.updateByPrimaryKey(record)>0;
    }

    /**
     * 分页查询歌曲的实现
     * @param pageNum 分页的数量
     * @param pageSize 分页的大小
     * @return
     */
    @Override
    public IPage<SongVo>  selectSongByPage(Integer pageNum, Integer pageSize) {
        Page<Song> page = new Page<>(pageNum,pageSize); // 查询pagenum页，pagesize的数据
        return songMapper.selectSongByPage(page);
    }

    @Override
    public Long selectSongByUrl(String songUrl) {
        return songMapper.selectSongByUrl(songUrl);
    }

    @Override
    public IPage<SongVo> selectSongBySongListId(Integer pageNum, Integer pageSize,Integer songListId) {
        IPage<SongVo> page = new Page<>(pageNum, pageSize);
       return songMapper.selectSongBySongListId(page,songListId);
    }

    @Override
    public IPage<SongVo> querySongBySongNameOrSingerName(Integer pageNum, Integer pageSize, String queryName) {
        IPage<SongVo> page = new Page<>(pageNum, pageSize);
        return songMapper.querySongBySongNameOrSingerName(page,queryName);
    }

    @Override
    public IPage<SongVo> selectSongBySingerId(Integer pageNum, Integer pageSize, Integer singerId) {
        IPage<SongVo> page = new Page<>(pageNum, pageSize);
        return  songMapper.selectSongBySingerId(page, singerId);
    }
    @Override
    public List<Song> songOfName(String songName) {
        return songMapper.songName(songName);
    }

    /**
     * 返回20首歌曲
     * todo 待完善
     * @return
     */
    @Override
    public AjaxResponse getHotSong() {
       List<SongVo> list =  songMapper.getHotSong();
        return AjaxResponse.success(list);
    }

    /**
     * 返回20首歌曲
     * todo 待完善
     * @param userId
     * @return
     */
    @Override
    public AjaxResponse getRecommendSong(Long userId) {
        List<SongVo>  list = songMapper.getRecommendSong();
        return AjaxResponse.success(list);
    }

}
