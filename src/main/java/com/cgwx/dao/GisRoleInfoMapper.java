package com.cgwx.dao;

import com.cgwx.data.entity.GisRoleInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface GisRoleInfoMapper {
    int insert(GisRoleInfo record);

    List<GisRoleInfo> selectAll();
}