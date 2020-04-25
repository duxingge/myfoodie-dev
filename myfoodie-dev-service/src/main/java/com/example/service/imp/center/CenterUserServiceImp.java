package com.example.service.imp.center;

import com.example.mapper.UsersMapper;
import com.example.pojo.Users;
import com.example.pojo.bo.center.CenterUserBO;
import com.example.service.center.CenterUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CenterUserServiceImp implements CenterUserService {

    @Autowired
    private UsersMapper usersMapper;


    @Override
    public Users queryUserInfo(String userId) {
        Users users = usersMapper.selectByPrimaryKey(userId);
        users.setPassword(null);
        return users;
    }

    @Override
    public Users updateUserInfo(String userId,CenterUserBO centerUserBO) {
        Users users = new Users();
        BeanUtils.copyProperties(centerUserBO,users);
        users.setId(userId);
        users.setUpdatedTime(new Date());
        usersMapper.updateByPrimaryKeySelective(users);
        return queryUserInfo(userId);
    }

    @Override
    public Users updateUserFace(String userId, String faceUrl) {
        Users user = new Users();
        user.setFace(faceUrl);
        user.setId(userId);
        usersMapper.updateByPrimaryKeySelective(user);
        return queryUserInfo(userId);
    }
}
