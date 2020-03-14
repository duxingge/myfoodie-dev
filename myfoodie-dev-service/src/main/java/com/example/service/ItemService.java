package com.example.service;

import com.example.pojo.Items;
import com.example.pojo.ItemsImg;
import com.example.pojo.ItemsParam;
import com.example.pojo.ItemsSpec;
import com.example.pojo.vo.CommentLevelCountsVO;
import com.example.pojo.vo.SearchItemVO;
import com.example.pojo.vo.ShopcartVO;
import com.example.utils.PagedGridResult;

import java.util.List;

public interface ItemService {
    PagedGridResult searhItems(String keyWords, String sort, Integer page, Integer pageSize);
    PagedGridResult searhItemsByThirdCat(Integer catId, String sort, Integer page, Integer pageSize);
    Items getByItemId(String itemId);
    List<ItemsImg> getImgsByItemId(String itemId);
    List<ItemsSpec> getSpecsByItemId(String itemId);
    ItemsParam getParamByItemId(String itemId);
    PagedGridResult queryItemComments(String itemId,Integer commentLevel,Integer page,Integer pageSize);
    CommentLevelCountsVO queryCommentCounts(String itemId);
    List<ShopcartVO> queryItemsBySpecIds(List<String> specList);
}
