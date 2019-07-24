package com.cgwx.dao;

import com.cgwx.data.entity.GisArchiveStatisticsAnalysisInfo;
import java.util.List;

public interface GisArchiveStatisticsAnalysisInfoMapper {
    int insert(GisArchiveStatisticsAnalysisInfo record);

    List<GisArchiveStatisticsAnalysisInfo> selectAll();
}