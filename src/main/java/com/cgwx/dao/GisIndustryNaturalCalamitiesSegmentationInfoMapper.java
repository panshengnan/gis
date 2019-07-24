package com.cgwx.dao;

import com.cgwx.data.entity.GisIndustryNaturalCalamitiesSegmentationInfo;
import java.util.List;

public interface GisIndustryNaturalCalamitiesSegmentationInfoMapper {
    int insert(GisIndustryNaturalCalamitiesSegmentationInfo record);

    List<GisIndustryNaturalCalamitiesSegmentationInfo> selectAll();
}