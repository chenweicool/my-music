package com.mymusic.controller.system;

import com.mymusic.common.exception.AjaxResponse;
import com.mymusic.domain.Rank;
import com.mymusic.service.impl.RankServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "用户评分接口")
@RestController
@Controller
public class RankController {

    @Autowired
    private RankServiceImpl rankService;

//    提交评分
    @ResponseBody
    @RequestMapping(value = "/rank/add", method = RequestMethod.POST)
    public AjaxResponse addRank(HttpServletRequest req){
        String songListId = req.getParameter("songListId").trim();
        String consumerId = req.getParameter("consumerId").trim();
        String score = req.getParameter("score").trim();

        Rank rank = new Rank();
        rank.setSongListId(Long.parseLong(songListId));
        rank.setConsumerId(Long.parseLong(consumerId));
        rank.setScore(Integer.parseInt(score));

        boolean res = rankService.addRank(rank);
        if (res){
            return AjaxResponse.success("评分成功");
        }else {
            return AjaxResponse.error("评分失败");
        }
    }

    /**
     * 获取指定歌单的评分
     * @param req 请求的信息
     * @return 具体的评分信息
     */
    @RequestMapping(value = "/rank", method = RequestMethod.GET)
    public Object rankOfSongListId(HttpServletRequest req){
        String songListId = req.getParameter("songListId");
        Integer rank = rankService.rankOfSongListId(Long.parseLong(songListId));
        return rank;
    }

}
