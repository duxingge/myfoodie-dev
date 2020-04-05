package com.example.mapper;

import com.example.my.mapper.MyMapper;
import com.example.pojo.UserAddress;
import org.springframework.stereotype.Component;

@Component
public interface UserAddressMapper extends MyMapper<UserAddress> {
}