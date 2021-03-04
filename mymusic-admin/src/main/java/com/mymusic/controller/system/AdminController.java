package com.mymusic.controller.system;

import com.mymusic.common.exception.AjaxResponse;
import com.mymusic.common.exception.CustomExceptionType;
import com.mymusic.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 管理员的登陆管理的实现
 */
@Api(tags = "管理员的管理")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    /**
     * 管理员的信息校验
     * @param request 请求的信息
     * @param session 存储给管理员的信息
     * @return 校验是否成功
     */
    @ApiOperation(value = "管理员",notes = "管理员登陆校验")
    @RequestMapping(value = "/login/status",method = RequestMethod.POST)
    public AjaxResponse loginStatus(HttpServletRequest request, HttpSession session) {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        boolean flag = adminService.verifyAdmin(name, password);
        if (flag) {
            return AjaxResponse.success("登陆成功");
        }
        return AjaxResponse.error(CustomExceptionType.SYSTEM_ERROR,"系统的信息异常,请联系管理员");
    }
}
