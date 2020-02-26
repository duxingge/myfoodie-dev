package com.example.service.imp;

import com.example.mapper.CategoryMapper;
import com.example.mapper.CategoryMapperCustom;
import com.example.pojo.Category;
import com.example.pojo.vo.CategoryVO;
import com.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryMapperCustom categoryMapperCustom;


    @Override
    public List<Category> getAllRootLevelCat() {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type",1);
        return categoryMapper.selectByExample(example);
    }

    @Override
    public List<CategoryVO> getSubCat(Integer rootCatId) {
        return categoryMapperCustom.getSubcat(rootCatId);
    }
}
