package com.example.service;

import com.example.pojo.Category;
import com.example.pojo.vo.CategoryVO;

import java.util.List;

public interface CategoryService {
    public List<Category> getAllRootLevelCat();
    public List<CategoryVO> getSubCat(Integer rootCatId);
}
