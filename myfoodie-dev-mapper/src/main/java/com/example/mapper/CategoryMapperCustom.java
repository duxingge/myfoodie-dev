package com.example.mapper;

import com.example.pojo.vo.CategoryVO;
import com.example.pojo.vo.NewItemVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CategoryMapperCustom {
    List<CategoryVO> getSubcat(Integer rootCatId);
    List<NewItemVo> getCatSixNewItem(@Param("paramsMap") Map map);
}
