package com.cgwx.dao;

import com.cgwx.data.entity.GisCartInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface GisCartInfoMapper {
    int insert(GisCartInfo record);

    List<GisCartInfo> selectAll();
}