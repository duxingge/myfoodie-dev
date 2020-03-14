package com.example.service.imp;

import com.example.enums.CommentLevel;
import com.example.mapper.*;
import com.example.pojo.*;
import com.example.pojo.vo.CommentLevelCountsVO;
import com.example.pojo.vo.ItemCommentVO;
import com.example.pojo.vo.SearchItemVO;
import com.example.pojo.vo.ShopcartVO;
import com.example.service.ItemService;
import com.example.utils.DesensitizationUtil;
import com.example.utils.PagedGridResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImp implements ItemService {

    @Autowired
    private ItemsMapperCustom mapperCustom;

    @Autowired
    private ItemsMapper itemsMapper;

    @Autowired
    private ItemsImgMapper imgMapper;

    @Autowired
    private ItemsSpecMapper specMapper;

    @Autowired
    private ItemsParamMapper paramMapper;

    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult searhItems(String keyWords, String sort, Integer page, Integer pageSize) {
        Map<String,Object> m = new HashMap<>();
        m.put("keywords",keyWords);
        m.put("sort",sort);
        PageHelper.startPage(page,pageSize);
        List<SearchItemVO> searchItemVOS = mapperCustom.searchItem(m);
        PagedGridResult gridResult = createGridResult(page, searchItemVOS);

        return gridResult;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult searhItemsByThirdCat(Integer catId, String sort, Integer page, Integer pageSize) {
        Map<String,Object> m = new HashMap<>();
        m.put("catId",catId);
        m.put("sort",sort);
        PageHelper.startPage(page,pageSize);
        List<SearchItemVO> searchItemVOS = mapperCustom.searchItemByThirdCat(m);
        PagedGridResult gridResult = createGridResult(page, searchItemVOS);

        return gridResult;
    }



    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Items getByItemId(String itemId) {
        return itemsMapper.selectByPrimaryKey(itemId);
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsImg> getImgsByItemId(String itemId) {
        Example example = new Example(ItemsImg.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        return imgMapper.selectByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsSpec> getSpecsByItemId(String itemId) {
        Example example = new Example(ItemsSpec.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        return specMapper.selectByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsParam getParamByItemId(String itemId) {
        Example example = new Example(ItemsParam.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        return paramMapper.selectOneByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryItemComments(String itemId, Integer commentLevel, Integer page, Integer pageSize) {
        Map<String,Object> m = new HashMap<>();
        m.put("itemId",itemId);
        m.put("level",commentLevel);
        PageHelper.startPage(page,pageSize);
        List<ItemCommentVO> itemCommentVOS = mapperCustom.queryItemComments(m);
        itemCommentVOS.stream().forEach(t->t.setNickname(DesensitizationUtil.commonDisplay(t.getNickname())));
        PagedGridResult gridResult = createGridResult(page, itemCommentVOS);

        return gridResult;

    }

    private PagedGridResult createGridResult(Integer page, List<?> searchItemVOS) {
        PageInfo<?> pageInfo = PageInfo.of(searchItemVOS);
        PagedGridResult gridResult = new PagedGridResult();
        gridResult.setPage(page);
        gridResult.setTotal(pageInfo.getPages());
        gridResult.setRecords(pageInfo.getTotal());
        gridResult.setRows(searchItemVOS);
        return gridResult;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public CommentLevelCountsVO queryCommentCounts(String itemId) {
        Integer goodCounts = getCommentCounts(itemId, CommentLevel.GOOD.type);
        Integer normalCounts = getCommentCounts(itemId, CommentLevel.NORMAL.type);
        Integer badCounts = getCommentCounts(itemId, CommentLevel.BAD.type);
        Integer totalCounts = goodCounts + normalCounts + badCounts;

        CommentLevelCountsVO countsVO = new CommentLevelCountsVO();
        countsVO.setTotalCounts(totalCounts);
        countsVO.setGoodCounts(goodCounts);
        countsVO.setNormalCounts(normalCounts);
        countsVO.setBadCounts(badCounts);

        return countsVO;
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    Integer getCommentCounts(String itemId, Integer level) {
        ItemsComments condition = new ItemsComments();
        condition.setItemId(itemId);
        if (level != null) {
            condition.setCommentLevel(level);
        }
        return itemsCommentsMapper.selectCount(condition);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ShopcartVO> queryItemsBySpecIds(List<String> specList) {
        return mapperCustom.queryItemsBySpecIds(specList);

    }

}
