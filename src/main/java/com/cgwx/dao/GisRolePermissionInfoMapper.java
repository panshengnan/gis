package com.cgwx.dao;

import com.cgwx.data.entity.GisRolePermissionInfo;
import java.util.List;

public interface GisRolePermissionInfoMapper {
    int insert(GisRolePermissionInfo record);

    List<GisRolePermissionInfo> selectAll();
}