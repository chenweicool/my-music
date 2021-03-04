package com.mymusic.controller.system;


import com.mymusic.common.exception.AjaxResponse;
import com.mymusic.domain.SysDict;
import com.mymusic.service.impl.SysDictServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 数据字典表 前端控制器
 * </p>
 *
 * @author chen
 * @since 2021-01-16
 */
@Api(tags = "数据字典表")
@RestController
@RequestMapping("/sysdict")
public class SysDictController {
    @Resource
    private SysDictServiceImpl sysDictService;

    /**
     * 查询所有
     * @return List<SysDict> 所有数据字典项
     */
    @PostMapping(value = "/all")
    public List<SysDict> all() {
        return sysDictService.all();
    }

    /**
     * 根据查询参数参训数据字典
     * @param groupName 分组名称
     * @param groupCode 分组编码
     * @return 数据字典项列表
     */
    @PostMapping(value = "/query")
    public List<SysDict> query(
            @RequestParam("groupName") String groupName,
            @RequestParam("groupCode") String groupCode  ) {
        return sysDictService.query(
                groupName,
                groupCode );
    }

    /**
     * 根据id更新数据数据字典项目
     * @param sysDict 更新实体（必须包含id）
     * @return 更新成功结果
     */
    @PostMapping(value = "/update")
    public AjaxResponse update(@RequestBody SysDict sysDict) {
        sysDictService.update(sysDict);
        return AjaxResponse.success("更新数据字典项成功！");
    }

    /**
     * 新增数据字典项
     * @param sysDict 新增实体
     * @return 更新成功结果
     */
    @PostMapping(value = "/add")
    public AjaxResponse add(@RequestBody SysDict sysDict) {
        sysDictService.add(sysDict);
        return AjaxResponse.success("新增数据字典项成功！");
    }

    /**
     * 根据id删除数据字典项
     * @param id 删除项id
     * @return 删除成功结果
     */
    @PostMapping(value = "/delete")
    public AjaxResponse delete(@RequestParam Long id) {
        sysDictService.delete(id);
        return AjaxResponse.success("删除数据字典项成功!");
    }

}

