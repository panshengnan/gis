package com.cgwx.dao;

import com.cgwx.data.entity.GisPermissionInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface GisPermissionInfoMapper {
    int insert(GisPermissionInfo record);

    List<GisPermissionInfo> selectAll();
}