package com.cgwx.dao;

import com.cgwx.data.entity.GisIndustryOthersSegmentationInfo;
import java.util.List;

public interface GisIndustryOthersSegmentationInfoMapper {
    int insert(GisIndustryOthersSegmentationInfo record);

    List<GisIndustryOthersSegmentationInfo> selectAll();
}