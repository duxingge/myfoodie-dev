package com.example.service.center;

import com.example.pojo.Users;
import com.example.pojo.bo.center.CenterUserBO;

public interface CenterUserService {
    Users queryUserInfo(String userId);

    Users updateUserInfo(String userId,CenterUserBO centerUserBO);

    Users updateUserFace(String userId, String imgUrl);
}
