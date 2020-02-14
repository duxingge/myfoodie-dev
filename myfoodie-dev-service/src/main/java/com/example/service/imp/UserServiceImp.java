package com.example.service.imp;

import com.example.enums.Sex;
import com.example.mapper.UsersMapper;
import com.example.pojo.Users;
import com.example.pojo.bo.UserBo;
import com.example.service.UserService;
import com.example.utils.DateUtil;
import com.example.utils.MD5Utils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private Sid sid;

    private static final String USER_FACE = "http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean isUserNameExit(String userName) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",userName);
        Users users = usersMapper.selectOneByExample(example);
        if (users==null) {
            return false;
        }else {
            return true;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users createUser(UserBo userBo) {
        String shortId = sid.nextShort();
        Users user = new Users();
        user.setId(shortId);
        user.setUsername(userBo.getUsername());
        try {
            user.setPassword(MD5Utils.getMD5Str(userBo.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        user.setNickname(userBo.getUsername());
        user.setFace(USER_FACE);
        user.setBirthday(DateUtil.stringToDate("1900-01-01"));
        //默认性别保密
        user.setSex(Sex.SECRET.type);
        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());
        usersMapper.insert(user);
        return user;
    }



    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserForLogin(String userName, String password) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",userName);
        criteria.andEqualTo("password",password);
        return usersMapper.selectOneByExample(example);
    }


}
