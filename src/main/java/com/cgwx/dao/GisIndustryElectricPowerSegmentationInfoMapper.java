package com.cgwx.dao;

import com.cgwx.data.entity.GisIndustryElectricPowerSegmentationInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface GisIndustryElectricPowerSegmentationInfoMapper {
    int insert(GisIndustryElectricPowerSegmentationInfo record);

    List<GisIndustryElectricPowerSegmentationInfo> selectAll();
}