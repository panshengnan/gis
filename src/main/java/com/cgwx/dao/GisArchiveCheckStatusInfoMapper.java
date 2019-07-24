package com.cgwx.dao;

import com.cgwx.data.entity.GisArchiveCheckStatusInfo;
import java.util.List;

public interface GisArchiveCheckStatusInfoMapper {
    int insert(GisArchiveCheckStatusInfo record);

    List<GisArchiveCheckStatusInfo> selectAll();
}