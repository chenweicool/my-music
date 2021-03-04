package com.mymusic.controller.system;


import com.mymusic.common.exception.AjaxResponse;
import com.mymusic.domain.SysOrg;
import com.mymusic.domain.SysUser;
import com.mymusic.model.SysOrgNode;
import com.mymusic.service.impl.SysOrgServiceImpl;
import com.mymusic.service.impl.SysUserServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 系统组织结构表 前端控制器
 * </p>
 *
 * @author chen
 * @since 2021-01-16
 */
@Api(tags = "组织的接口表")
@RestController
@RequestMapping("/sysorg")
public class SysOrgController {

    @Resource
    private SysOrgServiceImpl sysorgService;
    @Resource
    private SysUserServiceImpl sysuserService;

    // TODO 用户类需要单独实现
    @PostMapping(value = "/tree")
    public List<SysOrgNode> tree(@RequestParam("username") String username,
                                 @RequestParam("orgNameLike") String orgNameLike,
                                 @RequestParam("orgStatus") Boolean orgStatus) {
        SysUser sysUser = sysuserService.getUserByUserName(username);
        return sysorgService.getOrgTreeById(sysUser.getOrgId(), orgNameLike, orgStatus);
    }

    @PostMapping(value = "/update")
    public AjaxResponse update(@RequestBody SysOrg sysOrg) {
        sysorgService.updateOrg(sysOrg);
        return AjaxResponse.success("更新组织机构成功！");
    }

    @PostMapping(value = "/add")
    public AjaxResponse add(@RequestBody SysOrg sysOrg) {
        sysorgService.addOrg(sysOrg);
        return AjaxResponse.success("新增组织机构成功！");
    }


    @PostMapping(value = "/delete")
    public AjaxResponse delete(@RequestBody SysOrg sysOrg) {
        sysorgService.deleteOrg(sysOrg);
        return AjaxResponse.success("删除组织机构成功!");
    }

    //组织管理：更新组织禁用状态
    @PostMapping(value = "/status/change")
    public AjaxResponse update(@RequestParam Long orgId,
                               @RequestParam Boolean status) {
        sysorgService.updateStatus(orgId, status);
        return AjaxResponse.success("组织禁用状态更新成功！");
    }

}

