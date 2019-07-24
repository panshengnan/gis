package com.cgwx.dao;

import com.cgwx.data.entity.GisProductLayerInfo;
import java.util.List;

public interface GisProductLayerInfoMapper {
    int insert(GisProductLayerInfo record);

    List<GisProductLayerInfo> selectAll();
}