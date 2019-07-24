package com.cgwx.dao;

import com.cgwx.data.entity.GisArchiveRecordsInfo;
import java.util.List;

public interface GisArchiveRecordsInfoMapper {
    int insert(GisArchiveRecordsInfo record);

    List<GisArchiveRecordsInfo> selectAll();
}