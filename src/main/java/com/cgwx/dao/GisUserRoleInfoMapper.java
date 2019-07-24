package com.cgwx.dao;

import com.cgwx.data.entity.GisUserRoleInfo;
import java.util.List;

public interface GisUserRoleInfoMapper {
    int insert(GisUserRoleInfo record);

    List<GisUserRoleInfo> selectAll();
}