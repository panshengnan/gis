package com.cgwx.dao;

import com.cgwx.data.entity.GisIndustryCitySegmentationInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface GisIndustryCitySegmentationInfoMapper {
    int insert(GisIndustryCitySegmentationInfo record);

    List<GisIndustryCitySegmentationInfo> selectAll();
}