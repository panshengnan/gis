package com.cgwx.dao;

import com.cgwx.data.entity.GisIndustryTopographicFeaturesSegmentationInfo;
import java.util.List;

public interface GisIndustryTopographicFeaturesSegmentationInfoMapper {
    int insert(GisIndustryTopographicFeaturesSegmentationInfo record);

    List<GisIndustryTopographicFeaturesSegmentationInfo> selectAll();
}