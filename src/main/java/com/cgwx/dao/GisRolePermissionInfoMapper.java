package com.cgwx.dao;

import com.cgwx.data.entity.GisRolePermissionInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface GisRolePermissionInfoMapper {
    int insert(GisRolePermissionInfo record);

    List<GisRolePermissionInfo> selectAll();
}