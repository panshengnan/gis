package com.cgwx.dao;

import com.cgwx.data.entity.GisIndustryEnvironmentalProtectionSegmentationInfo;
import java.util.List;

public interface GisIndustryEnvironmentalProtectionSegmentationInfoMapper {
    int insert(GisIndustryEnvironmentalProtectionSegmentationInfo record);

    List<GisIndustryEnvironmentalProtectionSegmentationInfo> selectAll();
}