package com.example.controller;


import com.example.pojo.Items;
import com.example.pojo.ItemsImg;
import com.example.pojo.ItemsParam;
import com.example.pojo.ItemsSpec;
import com.example.pojo.vo.ItemInfoVO;
import com.example.pojo.vo.ShopcartVO;
import com.example.service.ItemService;
import com.example.utils.IMOOCJSONResult;
import com.example.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Api(value = "商品接口",tags={"商品信息展示的相关接口"})
@RestController
@RequestMapping("items")
public class ItemController extends BaseController {

    @Autowired
    private ItemService itemService;



    @ApiOperation(value = "根据关键词搜索商品信息",notes = "根据关键词搜索商品信息",httpMethod = "GET")
    @GetMapping("/search")
    public IMOOCJSONResult search(
            @ApiParam(value = "搜索关键词",required = true)
            @RequestParam String keywords,
            @ApiParam(value = "排序", required = false)
            @RequestParam String sort,
            @ApiParam(value = "查询的第几页", required = false)
            @RequestParam Integer page,
            @ApiParam(value = "分页的每一页显示的条数",required = false)
            @RequestParam Integer pageSize) {

        if (StringUtils.isBlank(keywords)) {
            return IMOOCJSONResult.errorMsg(null);
        }

        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }

        PagedGridResult gridResult = itemService.searhItems(keywords, sort, page, pageSize);
        return IMOOCJSONResult.ok(gridResult);


    }


    @ApiOperation(value = "根据关键词搜索商品信息",notes = "根据关键词搜索商品信息",httpMethod = "GET")
    @GetMapping("/catItems")
    public IMOOCJSONResult catItems(
            @ApiParam(value = "目录ID",required = true)
            @RequestParam Integer catId,
            @ApiParam(value = "排序", required = false)
            @RequestParam String sort,
            @ApiParam(value = "查询的第几页", required = false)
            @RequestParam Integer page,
            @ApiParam(value = "分页的每一页显示的条数",required = false)
            @RequestParam Integer pageSize) {

        if (catId==null) {
            return IMOOCJSONResult.errorMsg(null);
        }

        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }

        PagedGridResult gridResult = itemService.searhItemsByThirdCat(catId, sort, page, pageSize);
        return IMOOCJSONResult.ok(gridResult);


    }



    @ApiOperation(value = "商品详情",notes = "点击商品查看商品详情",httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public IMOOCJSONResult search(
            @ApiParam(value = "商品ID",required = true)
            @PathVariable("itemId") String itemId) {

        if (StringUtils.isBlank(itemId)) {
            return IMOOCJSONResult.errorMsg(null);
        }
        ItemInfoVO itemInfoVO = new ItemInfoVO();
        Items item = itemService.getByItemId(itemId);
        List<ItemsImg> images = itemService.getImgsByItemId(itemId);
        List<ItemsSpec> specs = itemService.getSpecsByItemId(itemId);
        ItemsParam param = itemService.getParamByItemId(itemId);
        itemInfoVO.setItem(item);
        itemInfoVO.setItemImgList(images);
        itemInfoVO.setItemSpecList(specs);
        itemInfoVO.setItemParams(param);

        return IMOOCJSONResult.ok(itemInfoVO);
    }

    @ApiOperation(value = "商品评价数量详情",notes = "商品评价数量详情",httpMethod = "GET")
    @GetMapping("commentLevel")
    public IMOOCJSONResult commentLevel(
            @ApiParam(value = "商品ID",required = true)
            @RequestParam String itemId) {
        return IMOOCJSONResult.ok(itemService.queryCommentCounts(itemId));

    }




    @ApiOperation(value = "查询商品评价",notes = "查询商品评价",httpMethod = "GET")
    @GetMapping("/comments")
    public IMOOCJSONResult comments(
            @ApiParam(value = "商品ID",required = true)
            @RequestParam String itemId,
            @ApiParam(value = "好评级别", required = false)
            @RequestParam(required = false) Integer level,
            @ApiParam(value = "查询的第几页", required = false)
            @RequestParam(required = false) Integer page,
            @ApiParam(value = "分页的每一页显示的条数",required = false)
            @RequestParam(required = false) Integer pageSize) {

        if (StringUtils.isBlank(itemId)) {
            return IMOOCJSONResult.errorMsg(null);
        }

        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult gridResult = itemService.queryItemComments(itemId, level, page, pageSize);
        return IMOOCJSONResult.ok(gridResult);


    }

    // 用于用户长时间未登录网站，刷新购物车中的数据（主要是商品价格），类似京东淘宝
    @ApiOperation(value = "根据商品规格ids查找最新的商品数据", notes = "根据商品规格ids查找最新的商品数据", httpMethod = "GET")
    @GetMapping("/refresh")
    public IMOOCJSONResult refresh( @ApiParam(name = "itemSpecIds", value = "拼接的规格ids", required = true, example = "1001,1003,1005")
                                        @RequestParam String itemSpecIds) {
        if (StringUtils.isBlank(itemSpecIds)) {
            return IMOOCJSONResult.ok();
        }

        String[] specArr = itemSpecIds.split(",");
        List<String> specList = new ArrayList<>();
        Collections.addAll(specList,specArr);
        List<ShopcartVO> shopcartVOS = itemService.queryItemsBySpecIds(specList);
        return IMOOCJSONResult.ok(shopcartVOS);

    }

}
