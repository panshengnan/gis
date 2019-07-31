package com.cgwx.dao;

import com.cgwx.data.entity.GisDownloadStatisticsAnalysisInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface GisDownloadStatisticsAnalysisInfoMapper {
    int insert(GisDownloadStatisticsAnalysisInfo record);

    List<GisDownloadStatisticsAnalysisInfo> selectAll();
}