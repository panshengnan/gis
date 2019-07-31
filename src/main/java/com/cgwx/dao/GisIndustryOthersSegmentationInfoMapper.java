package com.cgwx.dao;

import com.cgwx.data.entity.GisIndustryOthersSegmentationInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface GisIndustryOthersSegmentationInfoMapper {
    int insert(GisIndustryOthersSegmentationInfo record);

    List<GisIndustryOthersSegmentationInfo> selectAll();
}