package com.cgwx.dao;

import com.cgwx.data.entity.GisIndustryTerritorySegmentationInfo;
import java.util.List;

public interface GisIndustryTerritorySegmentationInfoMapper {
    int insert(GisIndustryTerritorySegmentationInfo record);

    List<GisIndustryTerritorySegmentationInfo> selectAll();
}