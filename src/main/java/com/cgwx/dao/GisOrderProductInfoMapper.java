package com.cgwx.dao;

import com.cgwx.data.entity.GisOrderProductInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface GisOrderProductInfoMapper {
    int insert(GisOrderProductInfo record);

    List<GisOrderProductInfo> selectAll();
}