package com.example.mapper;

import com.example.pojo.vo.CategoryVO;

import java.util.List;

public interface CategoryMapperCustom {
    List<CategoryVO> getSubcat(Integer rootCatId);
}
