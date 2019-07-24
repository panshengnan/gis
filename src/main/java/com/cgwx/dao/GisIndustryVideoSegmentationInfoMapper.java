package com.cgwx.dao;

import com.cgwx.data.entity.GisIndustryVideoSegmentationInfo;
import java.util.List;

public interface GisIndustryVideoSegmentationInfoMapper {
    int insert(GisIndustryVideoSegmentationInfo record);

    List<GisIndustryVideoSegmentationInfo> selectAll();
}