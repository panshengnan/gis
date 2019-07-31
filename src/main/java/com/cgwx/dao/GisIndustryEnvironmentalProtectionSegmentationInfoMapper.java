package com.cgwx.dao;

import com.cgwx.data.entity.GisIndustryEnvironmentalProtectionSegmentationInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface GisIndustryEnvironmentalProtectionSegmentationInfoMapper {
    int insert(GisIndustryEnvironmentalProtectionSegmentationInfo record);

    List<GisIndustryEnvironmentalProtectionSegmentationInfo> selectAll();
}