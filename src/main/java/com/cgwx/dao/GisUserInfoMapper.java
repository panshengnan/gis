package com.cgwx.dao;

import com.cgwx.data.entity.GisUserInfo;
import java.util.List;

public interface GisUserInfoMapper {
    int insert(GisUserInfo record);

    List<GisUserInfo> selectAll();
}