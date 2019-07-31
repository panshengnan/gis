package com.cgwx.dao;

import com.cgwx.data.entity.GisIndustryHousingConstructionSegmentationInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface GisIndustryHousingConstructionSegmentationInfoMapper {
    int insert(GisIndustryHousingConstructionSegmentationInfo record);

    List<GisIndustryHousingConstructionSegmentationInfo> selectAll();
}