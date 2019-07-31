package com.cgwx.dao;

import com.cgwx.data.entity.GisArchiveStatisticsAnalysisInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface GisArchiveStatisticsAnalysisInfoMapper {
    int insert(GisArchiveStatisticsAnalysisInfo record);

    List<GisArchiveStatisticsAnalysisInfo> selectAll();
}