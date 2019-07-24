package com.cgwx.dao;

import com.cgwx.data.entity.GisArchiveCheckInfo;
import java.util.List;

public interface GisArchiveCheckInfoMapper {
    int insert(GisArchiveCheckInfo record);

    List<GisArchiveCheckInfo> selectAll();
}