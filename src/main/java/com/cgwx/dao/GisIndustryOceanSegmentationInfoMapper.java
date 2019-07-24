package com.cgwx.dao;

import com.cgwx.data.entity.GisIndustryOceanSegmentationInfo;
import java.util.List;

public interface GisIndustryOceanSegmentationInfoMapper {
    int insert(GisIndustryOceanSegmentationInfo record);

    List<GisIndustryOceanSegmentationInfo> selectAll();
}