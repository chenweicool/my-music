package com.mymusic.controller.system;


import com.mymusic.common.exception.AjaxResponse;
import com.mymusic.domain.SysMenu;
import com.mymusic.model.RoleCheckedIds;
import com.mymusic.model.SysMenuNode;
import com.mymusic.service.impl.SysMenuServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统菜单表 前端控制器
 * </p>
 *
 * @author chen
 * @since 2021-01-16
 */
@Api(tags = "菜单的表项")
@RestController
@RequestMapping("/sysmenu")
public class SysMenuController {

    @Resource
    private SysMenuServiceImpl sysmenuService;

    //菜单管理：查询
    @PostMapping(value = "/tree")
    public List<SysMenuNode> tree(@RequestParam("menuNameLike") String menuNameLike,
                                  @RequestParam("menuStatus") Boolean menuStatus) {

        return sysmenuService.getMenuTree(menuNameLike, menuStatus);
    }

    //菜单管理：修改
    @PostMapping(value = "/update")
    public AjaxResponse update(@RequestBody SysMenu sysMenu) {
        sysmenuService.updateMenu(sysMenu);
        return AjaxResponse.success("更新菜单项成功！");
    }

    //菜单管理：新增
    @PostMapping(value = "/add")
    public AjaxResponse add(@RequestBody SysMenu sysMenu) {
        sysmenuService.addMenu(sysMenu);
        return AjaxResponse.success("新增菜单项成功！");
    }

    //菜单管理：删除
    @PostMapping(value = "/delete")
    public AjaxResponse delete(@RequestBody SysMenu sysMenu) {
        sysmenuService.deleteMenu(sysMenu);
        return AjaxResponse.success("删除菜单项成功!");
    }

    //角色管理:菜单树展示（勾选项、展开项）
    @PostMapping(value = "/checkedtree")
    public Map<String, Object> checkedtree(@RequestParam Long roleId) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("tree", sysmenuService.getMenuTree("", null));
        ret.put("expandedKeys", sysmenuService.getExpandedKeys());
        ret.put("checkedKeys", sysmenuService.getCheckedKeys(roleId));
        return ret;
    }

    //角色管理：保存菜单勾选结果
    @PostMapping(value = "/savekeys")
    public AjaxResponse savekeys(@RequestBody RoleCheckedIds roleCheckedIds) {
        sysmenuService.saveCheckedKeys(roleCheckedIds.getRoleId(), roleCheckedIds.getCheckedIds());
        return AjaxResponse.success("保存菜单权限成功!");
    }

    //系统左侧菜单栏加载，根据登录用户名加载它可以访问的菜单项
    @PostMapping(value = "/tree/user")
    public List<SysMenuNode> usertree(@RequestParam("username") String username) {
        return sysmenuService.getMenuTreeByUsername(username);
    }


    //菜单管理：更新菜单禁用状态
    @PostMapping(value = "/status/change")
    public AjaxResponse update(@RequestParam Long menuId,
                               @RequestParam Boolean status) {
        sysmenuService.updateStatus(menuId, status);
        return AjaxResponse.success("菜单禁用状态更新成功！");
    }
}

