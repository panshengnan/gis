package com.cgwx.dao;

import com.cgwx.data.entity.GisIndustryWaterConservancySegmentationInfo;
import java.util.List;

public interface GisIndustryWaterConservancySegmentationInfoMapper {
    int insert(GisIndustryWaterConservancySegmentationInfo record);

    List<GisIndustryWaterConservancySegmentationInfo> selectAll();
}