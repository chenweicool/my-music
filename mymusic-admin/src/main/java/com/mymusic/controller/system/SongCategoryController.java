package com.mymusic.controller.system;

import com.mymusic.common.exception.AjaxResponse;
import com.mymusic.domain.SongCategory;
import com.mymusic.service.SongCategoryService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/category")
public class SongCategoryController {

    @Resource
    private SongCategoryService categoryService;

    @PostMapping("/add")
    public AjaxResponse insertCategory(HttpServletRequest request) {
        String categoryName = request.getParameter("categoryName");
        SongCategory songCategory = new SongCategory();
        songCategory.setCategoryName(categoryName);
        boolean res = categoryService.insert(songCategory);
        if (res) {
            return AjaxResponse.success();
        } else {
            return AjaxResponse.error("添加失败");
        }
    }

    @GetMapping("/delete")
    public AjaxResponse delete(HttpServletRequest request) {
        String id = request.getParameter("id");
        boolean res = categoryService.delete(Integer.parseInt(id));
        if (res) {
            return AjaxResponse.success();
        } else {
            return AjaxResponse.error("删除失败");
        }
    }

    @PostMapping("/update")
    public AjaxResponse update(HttpServletRequest request) {
        String id = request.getParameter("id");
        String categoryName = request.getParameter("categoryName");
        SongCategory songCategory = new SongCategory();
        songCategory.setId(Integer.parseInt(id));
        songCategory.setCategoryName(categoryName);
        boolean res = categoryService.update(songCategory);
        if (res) {
            return AjaxResponse.success();
        } else {
            return AjaxResponse.error("更新失败");
        }
    }
    @GetMapping("/selectAll")
    public List<SongCategory> selectAll() {
        return categoryService.selectAll();
    }

    @GetMapping("/category")
    public AjaxResponse selectOne(@RequestParam("id") String  id) {
        SongCategory songCategory = categoryService.selectById(Integer.parseInt(id));
        if (songCategory == null) {
            return AjaxResponse.error("查找的歌曲类别不存在");
        }else{
            return AjaxResponse.success(songCategory);
        }
    }
}
