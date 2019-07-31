package com.cgwx.dao;

import com.cgwx.data.entity.GisUserRoleInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface GisUserRoleInfoMapper {
    int insert(GisUserRoleInfo record);

    List<GisUserRoleInfo> selectAll();
}