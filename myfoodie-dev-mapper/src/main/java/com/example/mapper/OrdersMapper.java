package com.example.mapper;

import com.example.my.mapper.MyMapper;
import com.example.pojo.Orders;
import org.springframework.stereotype.Component;

@Component
public interface OrdersMapper extends MyMapper<Orders> {
}