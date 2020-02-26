package com.example.controller;


import com.example.service.CarouselService;
import com.example.service.CategoryService;
import com.example.utils.IMOOCJSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("index")
public class IndexController {

    @Autowired
    private CarouselService carouselService;

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/carousel")
    public IMOOCJSONResult carousel() {
        return IMOOCJSONResult.ok(carouselService.getAll());
    }

    /**
     * 首页分类展示需求：
     * 1. 第一次刷新主页查询大分类，渲染展示到首页
     * 2. 如果鼠标上移到大分类，则加载其子分类的内容，如果已经存在子分类，则不需要加载（懒加载）
     */
    @GetMapping("/cats")
    public IMOOCJSONResult cats() {
        return IMOOCJSONResult.ok(categoryService.getAllRootLevelCat());
    }

    @GetMapping("/subCat/{rootCatId}")
    public IMOOCJSONResult getSubCategory(@PathVariable Integer rootCatId) {
        return IMOOCJSONResult.ok(categoryService.getSubCat(rootCatId));
    }


}
