package com.mymusic.controller.system;

import com.mymusic.common.exception.AjaxResponse;
import com.mymusic.common.exception.CustomExceptionType;
import com.mymusic.common.request.AddCollectRequest;
import com.mymusic.common.utils.ParameterCheckUtils;
import com.mymusic.service.impl.CollectServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户收藏歌曲接口")
@RestController
@RequestMapping("/collection")
public class CollectController {

    @Autowired
    private CollectServiceImpl collectService;

    /**
     *收藏一个歌单
     * @param request {@link AddCollectRequest}
     * @return {@link AjaxResponse}
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public AjaxResponse addCollection(@RequestBody AddCollectRequest request){
        return collectService.addCollection(request);
    }

    /**
     * 根据用户的id信息用户收藏列表
     * @param pageNum 当前页默认1
     * @param pageSize 当前页的大小
     * @param userId 用户id的信息
     * @param type 收藏的类型，0--代表的时歌单信息 1--代表的是收藏的歌曲
     * @return
     */
    @RequestMapping(value = "/getCollectsByUserId", method = RequestMethod.GET)
    public AjaxResponse getCollectsByUserId(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                           @RequestParam(value = "pageSize",defaultValue = "20") Integer pageSize,
                                            @RequestParam("userId") String userId,@RequestParam("type") Integer type){
        Long userIdDB = Long.parseLong(userId);
        return collectService.getCollectsByUserId(pageNum, pageSize, userIdDB, type);
    }

   // 取消收藏的歌单
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public AjaxResponse deleteCollection(@RequestParam("songListId") String songListId ,@RequestParam("userId") Long userId){
        ParameterCheckUtils.checkParamIsBlank(songListId);
        Integer collectSongListId = Integer.parseInt(songListId);

        boolean res = collectService.deleteCollectBySongListId(collectSongListId,userId);
        if (res) {
            return AjaxResponse.success("已取消收藏");
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

