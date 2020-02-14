package com.example.controller;

import com.example.pojo.Users;
import com.example.pojo.bo.UserBo;
import com.example.service.UserService;
import com.example.utils.CookieUtils;
import com.example.utils.IMOOCJSONResult;
import com.example.utils.JsonUtils;
import com.example.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/passport")
@Api(value = "注册登录",tags = {"用于注册登录的相关接口"})
public class PassportController {

    @Autowired
    private UserService userService;


    @ApiOperation(value = "用户名是否存在", notes = "验证用户名是否存在，没有存在，" +
            "即当前用户名可用的话返回对象的status为200",httpMethod = "GET")
    @GetMapping("usernameIsExist")
    public IMOOCJSONResult userNameIsExit(@RequestParam @ApiParam(value = "用户名",required = true) String username) {
        //1.非空判断
        if (StringUtils.isEmpty(username)) {
            return IMOOCJSONResult.errorMsg("用户名不能为空");
        }
        //2.查找用户名是否存在
        if (userService.isUserNameExit(username)) {
            return IMOOCJSONResult.errorMsg("用户名已经存在");
        }
        //3.请求成功，用户名没有重复
        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "用户注册",notes = "用户注册",httpMethod = "POST")
    @PostMapping("/regist")
    public IMOOCJSONResult createUser(@RequestBody UserBo userBo,HttpServletRequest request,HttpServletResponse response) {
        //0.用户名和密码不能为空
        if(StringUtils.isEmpty(userBo.getUsername())
                ||StringUtils.isEmpty(userBo.getPassword())
                ||StringUtils.isEmpty(userBo.getConfirmPassword())){
            return IMOOCJSONResult.errorMsg("用户名或者密码不能为空");
        }
        //1.用户名必须不存在
        if (userService.isUserNameExit(userBo.getUsername())) {
            return IMOOCJSONResult.errorMsg("用户名已存在！");
        }
        //2.密码长度必须大于6
        if(userBo.getPassword().length()<6) {
            return IMOOCJSONResult.errorMsg("密码长度不能少于6");
        }
        //3.两次密码必须一致
        if(!userBo.getPassword().equals(userBo.getConfirmPassword())) {
            return IMOOCJSONResult.errorMsg("两次密码必须一致");
        }
        //4.注册用户
        Users users = userService.createUser(userBo);
        users = setNullProperty(users);
        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson(users),true);




        return IMOOCJSONResult.ok();

    }

    @ApiOperation(value = "用户登录",notes = "用户登录接口",httpMethod = "POST")
    @PostMapping("/login")
    public IMOOCJSONResult login(@RequestBody UserBo userBo, HttpServletRequest request, HttpServletResponse response) {

        String username = userBo.getUsername();
        String password = userBo.getPassword();

        //1.判断用户名和密码不能为空
        if(StringUtils.isEmpty(username)||StringUtils.isEmpty(password)) {
            return IMOOCJSONResult.errorMsg("用户名和密码不能为空");
        }
        //2.实现登录
        Users users = null;
        try {
            users = userService.queryUserForLogin(username, MD5Utils.getMD5Str(password));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(users==null) {
            return IMOOCJSONResult.errorMsg("用户名或者密码不正确");
        }

        users = setNullProperty(users);
        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson(users),true);

        // TODO 生成用户token，存入redis会话
        // TODO 同步购物车数据
        return IMOOCJSONResult.ok(users);


    }

    private Users setNullProperty(Users userResult) {
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }

    @ApiOperation(value = "用户登出",notes = "用户登出接口",httpMethod = "POST")
    @PostMapping("/logout")
    public IMOOCJSONResult logout(HttpServletRequest request,HttpServletResponse response){
        CookieUtils.deleteCookie(request,response,"user");

        // TODO 用户退出登录，需要清除购物车

        // TODO 分布式会话需要清除用户数据
        return IMOOCJSONResult.ok();
    }
}
