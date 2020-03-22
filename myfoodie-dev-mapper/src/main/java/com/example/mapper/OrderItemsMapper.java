package com.example.mapper;

import com.example.my.mapper.MyMapper;
import com.example.pojo.OrderItems;
import org.springframework.stereotype.Component;

@Component
public interface OrderItemsMapper extends MyMapper<OrderItems> {
}