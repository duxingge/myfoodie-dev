package com.example.mapper;

import com.example.pojo.vo.ItemCommentVO;
import com.example.pojo.vo.SearchItemVO;
import com.example.pojo.vo.ShopcartVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

public interface ItemsMapperCustom {
    List<SearchItemVO> searchItem(@Param("paramsMap") Map<String,Object> m);
    List<SearchItemVO> searchItemByThirdCat(@Param("paramsMap") Map<String,Object> paramsMap);
    List<ItemCommentVO> queryItemComments(@Param("paramsMap") Map<String,Object> paramsMap);
    List<ShopcartVO> queryItemsBySpecIds(@Param("paramsList") List<String> paramsList);
    Integer decreaseItemSpecStock(@Param("specId") String specId,
                              @Param("pendingCounts") Integer pendingCounts);
}
