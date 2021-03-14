package com.mymusic.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mymusic.domain.SysApi;
import com.mymusic.domain.SysMenu;
import com.mymusic.domain.SysOrg;
import com.mymusic.domain.SysUserOrg;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 系统实现的接口
 */
public interface MySystemMapper {

    /*查询组织树*/
    List<SysOrg> selectOrgTree(@Param("rootOrgId") Long rootOrgId,
                               @Param("orgNameLike") String orgNameLike,
                               @Param("orgStatus") Boolean orgStatus);

    /*查询菜单树*/
    List<SysMenu> selectMenuTree(@Param("rootMenuId") Long rootMenuId,
                                 @Param("menuNameLike") String menuNameLike,
                                 @Param("menuStatus") Boolean menuStatus);

    /*查询接口树*/
    List<SysApi> selectApiTree(@Param("rootApiId") Long rootApiId,
                               @Param("apiNameLike") String apiNameLike,
                               @Param("apiStatus") Boolean apiStatus);


    Integer insertRoleMenuIds(@Param("roleId") Long roleId,
                              @Param("checkedIds") List<Long> checkedIds);

    Integer insertRoleApiIds(@Param("roleId") Long roleId,
                             @Param("checkedIds") List<Long> checkedIds);

    List<String> selectApiExpandedKeys();

    List<String> selectMenuExpandedKeys();

    List<String> selectApiCheckedKeys(Long roleId);

    List<String> selectMenuCheckedKeys(Long roleId);

    // 实现分页
    IPage<SysUserOrg> selectUser(Page<SysUserOrg> page,
                                  @Param("avator") String avator ,
                                 @Param("sex") Integer sex ,
                                 @Param("introduction") String introduction,
                                 @Param("birth") Date birth,
                                 @Param("location") Boolean location,

                                 @Param("orgId") Long orgId,
                                 @Param("username") String username,
                                 @Param("phone") String phone,
                                 @Param("email") String email,
                                 @Param("enabled") Boolean enabled,
                                 @Param("createStartTime") Date createStartTime,
                                 @Param("createEndTime") Date createEndTime);

    List<String> getCheckedRoleIds(Long userId);

    Long insertUserRoleIds(@Param("userId") Long userId,
                           @Param("checkedIds") List<Long> checkedIds);

    List<SysMenu> selectMenuByUsername(@Param("username") String username);
}
