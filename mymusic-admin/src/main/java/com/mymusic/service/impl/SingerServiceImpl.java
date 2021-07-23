package com.mymusic.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mymusic.common.domain.StatisticsVo;
import com.mymusic.common.utils.Constants;
import com.mymusic.common.utils.FileUtils;
import com.mymusic.common.utils.TimeUtils;
import com.mymusic.common.config.CosProperties;
import com.mymusic.mapper.SingerMapper;
import com.mymusic.domain.Singer;
import com.mymusic.common.enums.SingerConsumerType;
import com.mymusic.common.exception.SingerException;
import com.mymusic.service.SingerService;
import org.aspectj.apache.bcel.classfile.Constant;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SingerServiceImpl implements SingerService{

    @Resource
    private SingerMapper singerMapper;

    @Resource
    private CosProperties cosProperties;

    /**
     * 添加歌手，默认添加图片的信息
     * @param singer 歌手
     * @return
     */
    @Override
    public boolean addSinger( Singer singer) {
        String hostName = cosProperties.getHostName();
        String defaultPic = Constants.DEFAULT_PIC;
        singer.setPic(defaultPic);
        return singerMapper.insert(singer) > 0;
    }

    @Override
    public boolean updateSinger(Singer singer) {

        // 查询该用户是否存在
        Singer singerDb = singerMapper.findByPrimaryKey(singer.getId());
        if (singerDb == null) {
            throw new SingerException(SingerConsumerType.SINGER_NOT_EXIST);
        }
        int result = singerMapper.updateSingerMsg(singer);
        return result > 0;
    }

    @Override
    public boolean deleteSinger(Integer id) {
        Singer singer = singerMapper.findByPrimaryKey(id);
        if (singer == null) {
            throw new SingerException(SingerConsumerType.SINGER_NOT_EXIST);
        }
        return singerMapper.delete(id)>0;
    }

    @Override
    public Singer findSinger(Integer id) {
        return singerMapper.findByPrimaryKey(id);
    }



    @Override
    public boolean addSingerIdSongId(Integer singerId, Long songId) {
        return singerMapper.addSingerIdSongId(singerId,songId) > 0;
    }

    @Override
    public IPage<Singer> singerOfSex(Integer pageNum, Integer pageSize,Integer sex) {
        IPage<Singer> page = new Page<>(pageNum, pageSize);
        return singerMapper.singerOfSex(page,sex);
    }

    @Override
    public IPage<Singer> singerOfName(Integer pageNum, Integer pageSize, String name) {
        IPage<Singer> page = new Page<>(pageNum, pageSize);
        return singerMapper.singerOfName(page,name);
    }

    @Override
    public IPage<Singer> getSingerByPage(Integer pageNum, Integer pageSize) {
        IPage<Singer> page = new Page<>(pageNum, pageSize);
        return  singerMapper.getSingerByPage(page);
    }

    @Override
    public IPage<Singer> getSingerByLocation(Integer pageNum, Integer pageSize, String location) {
        IPage<Singer> page = new Page<>(pageNum, pageSize);
        return singerMapper.getSingerByLocation(page,location);
    }

    /**
     * 获取60，70年代，80年代的歌曲，90年代（以歌手的出生日期来分）
     * @param pageNum
     * @param pageSize
     * @param age 前端枚举这几个类型 60 70 80 90 00
     * @return
     */
    @Override
    public IPage<Singer> getSingerByAge(Integer pageNum, Integer pageSize, int age) {
        IPage<Singer> page = new Page<>(pageNum, pageSize);
        String startTime = TimeUtils.getLocalDateRange(age);
        String endTime = TimeUtils.getLocalDateRange(age + 10);

        page = singerMapper.getSingerByAge(page,startTime,endTime);
        return page;
    }

    @Override
    public List<Singer> getSingerHot() {
        return singerMapper.getSingerHot();
    }

    @Override
    public Long getTotalSingers() {
        return singerMapper.getTotalSinger();
    }

    @Override
    public List<StatisticsVo> getSexSingers() {
        List<StatisticsVo> sexSingers = singerMapper.getSexSingers();
        List<StatisticsVo> resultSex = new ArrayList<>(sexSingers.size());
        for (StatisticsVo sex : sexSingers) {
            if (Integer.parseInt(sex.getName()) == 0) {
                sex.setName("女");
            }else if((Integer.parseInt(sex.getName()) == 1)){
                sex.setName("男");
            }else if((Integer.parseInt(sex.getName()) == 2)){
                sex.setName("组合");
            }else{
                sex.setName("未知组合");
            }
            resultSex.add(sex);
        }
        return  resultSex;
    }

    @Override
    public List<StatisticsVo> getMaxSongsOfSinger() {
        List<StatisticsVo> listSex = singerMapper.getMaxSongsOfSinger();
//        List<StatisticsVo> resultSex = new ArrayList<>(listSex.size());
//        for (StatisticsVo sex : listSex) {
//            if (Integer.parseInt(sex.getName()) == 0) {
//                sex.setName("女");
//            }else if((Integer.parseInt(sex.getName()) == 1)){
//                sex.setName("男");
//            }else{
//                sex.setName("组合");
//            }
//            resultSex.add(sex);
//        }
//        return resultSex;
        return listSex;
    }

    public static void main(String[] args) {

        String localDate = TimeUtils.getLocalDateRange(70);
        String localDate1 = TimeUtils.getLocalDateRange(80);
        System.out.println(localDate);
        System.out.println(localDate1);
    }

}
