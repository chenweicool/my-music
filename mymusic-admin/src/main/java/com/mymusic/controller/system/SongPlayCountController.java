package com.mymusic.controller.system;

import com.mymusic.common.exception.AjaxResponse;
import com.mymusic.common.utils.ParameterCheckUtils;
import com.mymusic.domain.SongPlayCount;
import com.mymusic.service.SongPlayCountService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户的播放历史
 */
@RestController
@RequestMapping("/playCount")
public class SongPlayCountController {

    @Resource
    private SongPlayCountService songPlayCountService;

    //增加播放的历史记录接口
    @RequestMapping("/add")
    public AjaxResponse addPlayCount(@RequestParam("userId") Long userId,@RequestParam("songId") Long songId) {
        SongPlayCount songPlayCount = new SongPlayCount();
        ParameterCheckUtils.checkParamIsBlank(userId, songId);

        songPlayCount.setUserId(userId);
        songPlayCount.setSongId(songId);
        Boolean playCount = songPlayCountService.addSongPlayCount(songPlayCount);
        if (playCount) {
            return AjaxResponse.success("添加成功");
        }else{
            return AjaxResponse.error("增加失败");
        }
    }

    // 查询歌曲总的播放次数的接口
    @RequestMapping("/getTotalPlayCount")
    public AjaxResponse getTotalPlayCount(@RequestParam("songId") Long songId) {
        Long totalPlayCount = songPlayCountService.getTotalPlayCount(songId);
        return AjaxResponse.success(totalPlayCount);
    }

    // 查询用户听歌数量的接口
    @RequestMapping("/getUserTotal")
    public AjaxResponse getTotalPlayCountByUserId(@RequestParam("userId") Long userId) {
        Integer totalByUserId = songPlayCountService.getTotalByUserId(userId);
        return AjaxResponse.success(totalByUserId);
    }

    // 查询用户具体播放历史的接口
    @RequestMapping("/getUserHistory")
    public AjaxResponse getPlayHistoryByUserId(@RequestParam("userId") Long userId) {
        List<SongPlayCount> getUserTotal = songPlayCountService.getPlayCountByUserId(userId);
        return AjaxResponse.success(getUserTotal);
    }
}
