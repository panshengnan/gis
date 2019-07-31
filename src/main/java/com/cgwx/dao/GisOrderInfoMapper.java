package com.cgwx.dao;

import com.cgwx.data.entity.GisOrderInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface GisOrderInfoMapper {
    int insert(GisOrderInfo record);

    List<GisOrderInfo> selectAll();
}