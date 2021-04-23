package com.mymusic.controller.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mymusic.common.domain.SongListCollectVo;
import com.mymusic.common.enums.ResultCodeEnum;
import com.mymusic.common.exception.AjaxResponse;
import com.mymusic.common.exception.CustomExceptionType;
import com.mymusic.common.request.AddCollectSongListRequest;
import com.mymusic.common.utils.ParameterCheckUtils;
import com.mymusic.domain.Collect;
import com.mymusic.service.impl.CollectServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Api(tags = "用户收藏歌曲接口")
@RestController
@RequestMapping("/collection")
public class CollectController {

    @Autowired
    private CollectServiceImpl collectService;

    /**
     *收藏一个歌单
     * @param request {@link AddCollectSongListRequest}
     * @return {@link AjaxResponse}
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public AjaxResponse addCollection(@RequestBody  AddCollectSongListRequest request){
        ParameterCheckUtils.checkParamIsBlank(request.getSongListId(),request.getUserId());


        boolean res = collectService.addCollectionSongList(request);
        if (res){
            return AjaxResponse.success("收藏成功");
        }else {
            return AjaxResponse.error(CustomExceptionType.USER_INPUT_ERROR, "收藏失败");
        }
    }

    //返回的指定用户ID收藏列表
    @RequestMapping(value = "/getCollectsByUserId", method = RequestMethod.GET)
    public AjaxResponse getCollectsByUserId(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                           @RequestParam(value = "pageSize",defaultValue = "20") Integer pageSize,
                                            @RequestParam("userId") String userId){
        Long userIdDB = Long.parseLong(userId);
        IPage<SongListCollectVo> collectList =  collectService.getCollectsByUserId(pageNum,pageSize,userIdDB);
        return AjaxResponse.success(collectList);
    }

   // 取消收藏的歌单
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public AjaxResponse deleteCollection(@RequestParam("songListId") String songListId){
        ParameterCheckUtils.checkParamIsBlank(songListId);
        Integer collectSongListId = Integer.parseInt(songListId);

        boolean res = collectService.deleteCollectBySongListId(collectSongListId);
        if (res) {
            return AjaxResponse.success("取消收藏成功");
        }else{
            return AjaxResponse.error(CustomExceptionType.SYSTEM_ERROR, "取消失败");
        }
    }

    @RequestMapping(value = "/getCountBySongListId", method = RequestMethod.GET)
    public AjaxResponse getCountCollectSongList(@RequestParam("songListId")String songListId){
        ParameterCheckUtils.checkParamIsBlank(songListId);
        Integer songListCollectID = Integer.parseInt(songListId);
        Integer collectCount = collectService.getCount(songListCollectID);
        return AjaxResponse.success(collectCount);
    }


}

