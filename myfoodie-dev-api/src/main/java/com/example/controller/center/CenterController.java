package com.example.controller.center;

import com.example.pojo.Users;
import com.example.service.UserService;
import com.example.service.center.CenterUserService;
import com.example.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "center - 用户中心",tags = {"用户信息展示相关接口"})
@RestController
@RequestMapping("center")
public class CenterController {

    @Autowired
    private CenterUserService centerUserService;

    @ApiOperation(value = "获取用户信息",httpMethod = "GET")
    @GetMapping("userInfo")
    public IMOOCJSONResult userInfo(@ApiParam(name = "userId",value = "用户Id") String userId) {
        Users userInfo = centerUserService.queryUserInfo(userId);
        return IMOOCJSONResult.ok(userInfo);
    }




}
