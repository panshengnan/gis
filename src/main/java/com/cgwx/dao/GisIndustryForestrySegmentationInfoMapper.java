package com.cgwx.dao;

import com.cgwx.data.entity.GisIndustryForestrySegmentationInfo;
import java.util.List;

public interface GisIndustryForestrySegmentationInfoMapper {
    int insert(GisIndustryForestrySegmentationInfo record);

    List<GisIndustryForestrySegmentationInfo> selectAll();
}