package com.cgwx.dao;

import com.cgwx.data.entity.GisPermissionInfo;
import java.util.List;

public interface GisPermissionInfoMapper {
    int insert(GisPermissionInfo record);

    List<GisPermissionInfo> selectAll();
}