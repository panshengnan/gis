package com.cgwx.dao;

import com.cgwx.data.entity.GisIndustryNoctilucentSegmentationInfo;
import java.util.List;

public interface GisIndustryNoctilucentSegmentationInfoMapper {
    int insert(GisIndustryNoctilucentSegmentationInfo record);

    List<GisIndustryNoctilucentSegmentationInfo> selectAll();
}