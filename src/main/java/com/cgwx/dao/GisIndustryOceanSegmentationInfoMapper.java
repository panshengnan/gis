package com.cgwx.dao;

import com.cgwx.data.entity.GisIndustryOceanSegmentationInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface GisIndustryOceanSegmentationInfoMapper {
    int insert(GisIndustryOceanSegmentationInfo record);

    List<GisIndustryOceanSegmentationInfo> selectAll();
}