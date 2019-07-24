package com.cgwx.dao;

import com.cgwx.data.entity.GisIndustryElectricPowerSegmentationInfo;
import java.util.List;

public interface GisIndustryElectricPowerSegmentationInfoMapper {
    int insert(GisIndustryElectricPowerSegmentationInfo record);

    List<GisIndustryElectricPowerSegmentationInfo> selectAll();
}