package com.cgwx.dao;

import com.cgwx.data.entity.GisIndustryTrafficSegmentationInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface GisIndustryTrafficSegmentationInfoMapper {
    int insert(GisIndustryTrafficSegmentationInfo record);

    List<GisIndustryTrafficSegmentationInfo> selectAll();
}