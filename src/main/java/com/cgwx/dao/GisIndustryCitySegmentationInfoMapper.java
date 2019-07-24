package com.cgwx.dao;

import com.cgwx.data.entity.GisIndustryCitySegmentationInfo;
import java.util.List;

public interface GisIndustryCitySegmentationInfoMapper {
    int insert(GisIndustryCitySegmentationInfo record);

    List<GisIndustryCitySegmentationInfo> selectAll();
}