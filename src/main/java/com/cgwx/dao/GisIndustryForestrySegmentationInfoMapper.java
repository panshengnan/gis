package com.cgwx.dao;

import com.cgwx.data.entity.GisIndustryForestrySegmentationInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface GisIndustryForestrySegmentationInfoMapper {
    int insert(GisIndustryForestrySegmentationInfo record);

    List<GisIndustryForestrySegmentationInfo> selectAll();
}