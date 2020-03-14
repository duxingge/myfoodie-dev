package com.example.service.imp;

import com.example.mapper.CategoryMapper;
import com.example.mapper.CategoryMapperCustom;
import com.example.pojo.Category;
import com.example.pojo.vo.CategoryVO;
import com.example.pojo.vo.NewItemVo;
import com.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImp implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryMapperCustom categoryMapperCustom;


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> getAllRootLevelCat() {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type",1);
        return categoryMapper.selectByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<CategoryVO> getSubCat(Integer rootCatId) {
        return categoryMapperCustom.getSubcat(rootCatId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<NewItemVo> getSixNewItem(String rootCatId) {
        Map<String,Object> m = new HashMap();
        m.put("rootCatId",rootCatId);
        return categoryMapperCustom.getCatSixNewItem(m);
    }
}
