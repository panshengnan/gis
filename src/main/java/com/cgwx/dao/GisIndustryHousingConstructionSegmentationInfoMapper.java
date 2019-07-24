package com.cgwx.dao;

import com.cgwx.data.entity.GisIndustryHousingConstructionSegmentationInfo;
import java.util.List;

public interface GisIndustryHousingConstructionSegmentationInfoMapper {
    int insert(GisIndustryHousingConstructionSegmentationInfo record);

    List<GisIndustryHousingConstructionSegmentationInfo> selectAll();
}