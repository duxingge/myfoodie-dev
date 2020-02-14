package com.example.mapper;

import com.example.my.mapper.MyMapper;
import com.example.pojo.Users;
import org.springframework.stereotype.Component;

@Component
public interface UsersMapper extends MyMapper<Users> {
}