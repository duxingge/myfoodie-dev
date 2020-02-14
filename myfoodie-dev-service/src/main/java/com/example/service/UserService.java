package com.example.service;

import com.example.pojo.Users;
import com.example.pojo.bo.UserBo;

public interface UserService {
    /**
     * 判断用户名是否存在
     * @param userName
     * @return
     */
    public boolean isUserNameExit(String userName);

    /**
     * 注册用户
     * @param userBo
     * @return
     */
    public Users createUser(UserBo userBo);

    /**
     * 用户登录查询
     * @param userName
     * @param password
     * @return
     */
    public Users queryUserForLogin(String userName,String password);

}
