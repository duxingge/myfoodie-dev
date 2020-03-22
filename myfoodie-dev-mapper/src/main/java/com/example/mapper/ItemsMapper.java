package com.example.mapper;

import com.example.my.mapper.MyMapper;
import com.example.pojo.Items;
import org.springframework.stereotype.Component;

@Component
public interface ItemsMapper extends MyMapper<Items> {
}