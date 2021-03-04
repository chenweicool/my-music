package com.mymusic.controller.system;

import com.mymusic.common.enums.ResultCodeEnum;
import com.mymusic.common.exception.AjaxResponse;
import com.mymusic.common.utils.Constants;
import com.mymusic.common.utils.FileUtils;
import com.mymusic.domain.Consumer;
import com.mymusic.service.ConsumerService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 用户的控制层
 */
@Api(tags = "普通用户的接口")
@RestController
@RequestMapping("/user")
public class ConsumerController {

    @Resource
    private ConsumerService consumerService;

    @Value("${file.Access_Key}")
    private String Access_Key;

    @Value("${file.Secret_Key}")
    private String Secret_Key;
    /**
     * 添加用户
     * //TODO 这里的验证需要统一处理一下。
     * @param req 请求的参数
     * @return 添加是否成功
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public AjaxResponse addUser(HttpServletRequest req){
        String username = req.getParameter("username").trim();
        String password = req.getParameter("password").trim();
        String sex = req.getParameter("sex").trim();
        String phone_num = req.getParameter("phone_num").trim();
        String email = req.getParameter("email").trim();
        String birth = req.getParameter("birth").trim();
        String introduction = req.getParameter("introduction").trim();
        String location = req.getParameter("location").trim();

        // 用户设置默认的头像信息
       String  avator = Constants.DEFAULT_PIC;

        if (username.equals("") || username == null){
             return AjaxResponse.setResult(ResultCodeEnum.USER_NAME_NOT_NULL);
        }
        Consumer consumer = new Consumer();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date myBirth = new Date();
        try {
            myBirth = dateFormat.parse(birth);
        } catch (Exception e){
            e.printStackTrace();
        }
        consumer.setUsername(username);
        consumer.setPassword(password);
        consumer.setSex(Integer.parseInt(sex));
        if (phone_num == "") {
            consumer.setPhoneNum(null);
        } else{
            consumer.setPhoneNum(phone_num);
        }

        if (email == "") {
            consumer.setEmail(null);
        } else{
            consumer.setEmail(email);
        }
        consumer.setBirth(myBirth);
        consumer.setIntroduction(introduction);
        consumer.setLocation(location);
        consumer.setAvator(avator);
        consumer.setCreateTime(new Date());
        consumer.setUpdateTime(new Date());

        boolean res = consumerService.addUser(consumer);
        if (res) {
            return AjaxResponse.success("添加成功");
        } else {
            return  AjaxResponse.error("添加失败");
        }
    }

    //    判断是否登录成功
    @ResponseBody
    @RequestMapping(value = "/login/status", method = RequestMethod.POST)
    public Object loginStatus(HttpServletRequest req, HttpSession session){

        String username = req.getParameter("username");
        String password = req.getParameter("password");
//        System.out.println(username+"  "+password);
        boolean res = consumerService.veritypasswd(username, password);

        if (res){
//            jsonObject.put("code", 1);
//            jsonObject.put("msg", "登录成功");
//            jsonObject.put("userMsg", consumerService.loginStatus(username));
              Consumer consumer =  consumerService.loginStatus(username);
            session.setAttribute("username", username);
            return AjaxResponse.success(consumer);
        }else {
            return AjaxResponse.error("用户名或者密码错误");
        }

    }

    //    返回所有用户
    @RequestMapping(value = "/alluser", method = RequestMethod.GET)
    public List<Consumer> allUser(){
        List<Consumer> allConsumer =  consumerService.allUser();
        return allConsumer;
    }

    /*根据用户id返回用户的信息*/
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Object userOfId(HttpServletRequest req){
        String id = req.getParameter("id");
        Consumer consumer =  consumerService.userOfId(Integer.parseInt(id));
        Date mybirth = consumer.getBirth();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
       // mybirth = dateFormat.format(mybirth);
        return consumer;
    }

    //    删除用户
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public AjaxResponse deleteUser(HttpServletRequest req){
        String id = req.getParameter("id");
        boolean res =  consumerService.deleteUser(Integer.parseInt(id));
        if (res) {
            return AjaxResponse.success("删除成功");
        } else {
            return  AjaxResponse.error("删除失败");
        }
    }

    /**
     * 更新用户的信息
     * @param req 请求的参数
     * @return 请求的结果
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public AjaxResponse updateUserMsg(HttpServletRequest req){
        String id = req.getParameter("id").trim();
        String username = req.getParameter("username").trim();
        String password = req.getParameter("password").trim();
        String sex = req.getParameter("sex").trim();
        String phone_num = req.getParameter("phone_num").trim();
        String email = req.getParameter("email").trim();
        String birth = req.getParameter("birth").trim();
        String introduction = req.getParameter("introduction").trim();
        String location = req.getParameter("location").trim();

        if (username.equals("") || username == null){
            return AjaxResponse.setResult(ResultCodeEnum.USER_NAME_NOT_NULL);
        }
        Consumer consumer = new Consumer();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date myBirth = new Date();
        try {
            myBirth = dateFormat.parse(birth);
        }catch (Exception e){
            e.printStackTrace();
        }
        consumer.setId(Integer.parseInt(id));
        consumer.setUsername(username);
        consumer.setPassword(password);
        consumer.setSex(Integer.parseInt(sex));
        consumer.setPhoneNum(phone_num);
        consumer.setEmail(email);
        consumer.setBirth(myBirth);
        consumer.setIntroduction(introduction);
        consumer.setLocation(location);
        consumer.setUpdateTime(new Date());

        boolean res = consumerService.updateUserMsg(consumer);
        if (res) {
            return AjaxResponse.success("更新成功");
        } else {
            return  AjaxResponse.error("更新失败");
        }
    }

    //    更新用户头像
    @ResponseBody
    @RequestMapping(value = "/avatar/update", method = RequestMethod.POST)
    public HashMap<String, Object> updateUserPic(@RequestParam("file") MultipartFile picFile, @RequestParam("id")int id) {

        HashMap<String, Object> res = new HashMap<>();
        if (picFile.isEmpty()) {
            res.put("文件夹不能为空", ResultCodeEnum.FILE_NOT_NULL);
            return res;
        }

        // 上传用户的头像信息到起七牛云
        String songPic = Constants.USER_PIC + System.currentTimeMillis() + picFile.getOriginalFilename();
        String picPath = FileUtils.getAvatarPic(picFile, Access_Key, Secret_Key, songPic);

        Consumer consumer = new Consumer();
        consumer.setId(id);
        consumer.setAvator(picPath);
        boolean flag = consumerService.updateUserAvator(consumer);
        if (flag) {
            res.put("avator", picPath);
            return res;
        } else {
            res.put("error", ResultCodeEnum.UNKNOWN_ERROR);
            return res;
        }
    }
}
