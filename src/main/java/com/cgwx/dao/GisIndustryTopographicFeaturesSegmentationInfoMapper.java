package com.cgwx.dao;

import com.cgwx.data.entity.GisIndustryTopographicFeaturesSegmentationInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface GisIndustryTopographicFeaturesSegmentationInfoMapper {
    int insert(GisIndustryTopographicFeaturesSegmentationInfo record);

    List<GisIndustryTopographicFeaturesSegmentationInfo> selectAll();
}