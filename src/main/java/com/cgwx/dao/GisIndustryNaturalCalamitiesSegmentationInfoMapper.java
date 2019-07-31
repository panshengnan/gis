package com.cgwx.dao;

import com.cgwx.data.entity.GisIndustryNaturalCalamitiesSegmentationInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface GisIndustryNaturalCalamitiesSegmentationInfoMapper {
    int insert(GisIndustryNaturalCalamitiesSegmentationInfo record);

    List<GisIndustryNaturalCalamitiesSegmentationInfo> selectAll();
}