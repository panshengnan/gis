package com.cgwx.dao;

import com.cgwx.data.entity.GisIndustryVideoSegmentationInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface GisIndustryVideoSegmentationInfoMapper {
    int insert(GisIndustryVideoSegmentationInfo record);

    List<GisIndustryVideoSegmentationInfo> selectAll();
}