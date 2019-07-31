package com.cgwx.service;


import com.cgwx.data.entity.GisUserInfo;


import java.util.List;

public interface IUserService {
    String register(GisUserInfo pdmUserInfo);
    String loginByPasswd(String account, String passwd);
    String modifyPasswdByAccount(String userId, String oldPasswd, String newPasswd);
    List<String> getUserList();
}
