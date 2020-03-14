package com.example.controller;

import com.example.pojo.bo.ShopcartBO;
import com.example.utils.IMOOCJSONResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("shopcart")
public class ShopcatController {

    @ApiOperation(value = "添加商品到购物车", notes = "添加商品到购物车", httpMethod = "POST")
    @PostMapping("/add")
    public IMOOCJSONResult add(
            @ApiParam(value = "用户ID",required = true)
            @RequestParam
            String userId,
            @ApiParam(value = "商品信息BO",required = true)
            @RequestBody
            ShopcartBO shopcartBO) {

        if (StringUtils.isEmpty(userId)) {
            IMOOCJSONResult.errorMsg(null);
        }

        System.out.println(shopcartBO);

        //TODO 将商品信息存入Redis中
        return IMOOCJSONResult.ok();

    }

    @ApiOperation(value = "从购物车中删除商品", notes = "从购物车中删除商品", httpMethod = "POST")
    @PostMapping("/del")
    public IMOOCJSONResult del(
            @RequestParam String userId,
            @RequestParam String itemSpecId,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(itemSpecId)) {
            return IMOOCJSONResult.errorMsg("参数不能为空");
        }

        // TODO 用户在页面删除购物车中的商品数据，如果此时用户已经登录，则需要同步删除后端购物车中的商品

        return IMOOCJSONResult.ok();
    }



}
