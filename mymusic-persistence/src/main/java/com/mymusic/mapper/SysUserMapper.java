package com.mymusic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mymusic.common.domain.StatisticsVo;
import com.mymusic.domain.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author chen
 * @since 2021-01-16
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    // 实现对用户的信息统计
    List<StatisticsVo> getStaticSex();

    /*实现用户地域的统计*/
    List<StatisticsVo> getStaticLocation();

    /*得到总的用户人数*/
    Long getTotalUsers();

    /*得到今日的新增人数*/
    Long getAddNumbers(@Param("startTime") String startTime, @Param("endTime") String endTime);
}
