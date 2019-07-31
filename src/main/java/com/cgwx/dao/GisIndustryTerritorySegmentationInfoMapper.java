package com.cgwx.dao;

import com.cgwx.data.entity.GisIndustryTerritorySegmentationInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface GisIndustryTerritorySegmentationInfoMapper {
    int insert(GisIndustryTerritorySegmentationInfo record);

    List<GisIndustryTerritorySegmentationInfo> selectAll();
}