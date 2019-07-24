package com.cgwx.dao;

import com.cgwx.data.entity.GisRoleInfo;
import java.util.List;

public interface GisRoleInfoMapper {
    int insert(GisRoleInfo record);

    List<GisRoleInfo> selectAll();
}