package com.example.controller;


import com.example.pojo.vo.NewItemVo;
import com.example.service.CarouselService;
import com.example.service.CategoryService;
import com.example.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(value = "首页",tags = "首页展示相关接口")
@RestController
@RequestMapping("index")
public class IndexController {

    @Autowired
    private CarouselService carouselService;

    @Autowired
    private CategoryService categoryService;



    @ApiOperation(value = "获取首页轮播图列表",notes = "获取首页轮播图列表",httpMethod = "GET")
    @GetMapping("/carousel")
    public IMOOCJSONResult carousel() {
        return IMOOCJSONResult.ok(carouselService.getAll());
    }

    /**
     * 首页分类展示需求：
     * 1. 第一次刷新主页查询大分类，渲染展示到首页
     * 2. 如果鼠标上移到大分类，则加载其子分类的内容，如果已经存在子分类，则不需要加载（懒加载）
     */
    @ApiOperation(value = "获取商品分类(一级分类)",notes = "获取商品分类(一级分类)",httpMethod = "GET")
    @GetMapping("/cats")
    public IMOOCJSONResult cats() {
        return IMOOCJSONResult.ok(categoryService.getAllRootLevelCat());
    }

    @ApiOperation(value = "获取商品子分类",notes = "获取商品子分类",httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public IMOOCJSONResult getSubCategory(
            @ApiParam(value = "一级分类ID",required = true)
            @PathVariable Integer rootCatId) {
        return IMOOCJSONResult.ok(categoryService.getSubCat(rootCatId));
    }

    @ApiOperation(value = "获取商品子分类",notes = "获取商品子分类",httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    public IMOOCJSONResult getSixNewItem(
            @ApiParam(value = "一级目录ID",required = true )
            @PathVariable("rootCatId") String rootCatId) {
        List<NewItemVo> sixNewItem = categoryService.getSixNewItem(rootCatId);
        return IMOOCJSONResult.ok(sixNewItem);
    }

}
