package com.mymusic.controller.monitor;

import com.mymusic.common.exception.AjaxResponse;
import com.mymusic.domain.Server;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 得到系统的各个参数的控制类
 */
@RestController
@RequestMapping("/monitor")
public class ServerController {

    /**
     * 请求系统信息的实现
     * @return  返回这个结果。
     * @throws Exception
     */
    @GetMapping(value = "/server")
    public AjaxResponse getInfo() throws Exception
    {
        Server server = new Server();
        server.copyTo();
        return AjaxResponse.success(server);
    }
}
