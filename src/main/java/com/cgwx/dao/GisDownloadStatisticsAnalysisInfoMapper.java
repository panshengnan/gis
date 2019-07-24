package com.cgwx.dao;

import com.cgwx.data.entity.GisDownloadStatisticsAnalysisInfo;
import java.util.List;

public interface GisDownloadStatisticsAnalysisInfoMapper {
    int insert(GisDownloadStatisticsAnalysisInfo record);

    List<GisDownloadStatisticsAnalysisInfo> selectAll();
}