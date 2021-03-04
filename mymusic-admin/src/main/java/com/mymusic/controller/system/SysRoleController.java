package com.mymusic.controller.system;


import com.mymusic.common.exception.AjaxResponse;
import com.mymusic.domain.SysRole;
import com.mymusic.model.UserRoleCheckedIds;
import com.mymusic.service.impl.SysRoleServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色接口权限关系表 前端控制器
 * </p>
 *
 * @author chen
 * @since 2021-01-16
 */
@Api(tags = "角色的权限表")
@RestController
@RequestMapping("/sysrole")
public class SysRoleController {

    @Resource
    private SysRoleServiceImpl sysroleService;

    //角色管理:查询
    @PostMapping(value = "/query")
    public List<SysRole> query(@RequestParam("roleLike") String roleLike) {
        return sysroleService.queryRoles(roleLike);
    }

    //角色管理：修改
    @PostMapping(value = "/update")
    public AjaxResponse update(@RequestBody SysRole sysRole) {
        sysroleService.updateRole(sysRole);
        return AjaxResponse.success("更新角色成功！");
    }

    //角色管理：新增
    @PostMapping(value = "/add")
    public AjaxResponse add(@RequestBody SysRole sysRole) {
        sysroleService.addRole(sysRole);
        return AjaxResponse.success("新增角色成功！");
    }

    //角色管理：删除
    @PostMapping(value = "/delete")
    public AjaxResponse delete(@RequestParam Long roleId) {
        sysroleService.deleteRole(roleId);
        return AjaxResponse.success("删除角色成功!");
    }

    //用户管理：为用户分配角色，展示角色列表及勾选角色列表
    @PostMapping(value = "/checkedroles")
    public Map<String,Object> checkedroles(@RequestParam Long userId) {
        return sysroleService.getRolesAndChecked(userId);
    }

    //用户管理：保存用户角色
    @PostMapping(value = "/savekeys")
    public AjaxResponse savekeys(@RequestBody UserRoleCheckedIds userRoleCheckedIds) {
        sysroleService.saveCheckedKeys(userRoleCheckedIds.getUserId(),userRoleCheckedIds.getCheckedIds());
        return AjaxResponse.success("保存用户角色成功!");
    }


    //角色管理：更新角色禁用状态
    @PostMapping(value = "/status/change")
    public AjaxResponse update(@RequestParam Long roleId,
                               @RequestParam Boolean status) {
        sysroleService.updateStatus(roleId, status);
        return AjaxResponse.success("角色禁用状态更新成功！");
    }

}

