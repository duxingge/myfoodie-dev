package com.example.service;

import com.example.pojo.Category;
import com.example.pojo.vo.CategoryVO;
import com.example.pojo.vo.NewItemVo;

import java.util.List;

public interface CategoryService {
     List<Category> getAllRootLevelCat();
     List<CategoryVO> getSubCat(Integer rootCatId);
     List<NewItemVo> getSixNewItem(String rootcatId);
}
