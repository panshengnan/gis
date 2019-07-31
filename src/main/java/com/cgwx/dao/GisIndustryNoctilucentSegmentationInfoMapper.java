package com.cgwx.dao;

import com.cgwx.data.entity.GisIndustryNoctilucentSegmentationInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface GisIndustryNoctilucentSegmentationInfoMapper {
    int insert(GisIndustryNoctilucentSegmentationInfo record);

    List<GisIndustryNoctilucentSegmentationInfo> selectAll();
}