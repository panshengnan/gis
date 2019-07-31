package com.cgwx.dao;

import com.cgwx.data.entity.GisIndustryWaterConservancySegmentationInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface GisIndustryWaterConservancySegmentationInfoMapper {
    int insert(GisIndustryWaterConservancySegmentationInfo record);

    List<GisIndustryWaterConservancySegmentationInfo> selectAll();
}