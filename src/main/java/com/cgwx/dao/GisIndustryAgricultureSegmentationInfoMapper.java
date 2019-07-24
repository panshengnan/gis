package com.cgwx.dao;

import com.cgwx.data.entity.GisIndustryAgricultureSegmentationInfo;
import java.util.List;

public interface GisIndustryAgricultureSegmentationInfoMapper {
    int insert(GisIndustryAgricultureSegmentationInfo record);

    List<GisIndustryAgricultureSegmentationInfo> selectAll();
}