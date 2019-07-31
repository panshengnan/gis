package com.cgwx.dao;

import com.cgwx.data.entity.GisIndustryAgricultureSegmentationInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface GisIndustryAgricultureSegmentationInfoMapper {
    int insert(GisIndustryAgricultureSegmentationInfo record);

    List<GisIndustryAgricultureSegmentationInfo> selectAll();
}