package com.cgwx.dao;

import com.cgwx.data.entity.GisIndustryTrafficSegmentationInfo;
import java.util.List;

public interface GisIndustryTrafficSegmentationInfoMapper {
    int insert(GisIndustryTrafficSegmentationInfo record);

    List<GisIndustryTrafficSegmentationInfo> selectAll();
}