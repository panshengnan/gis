package com.cgwx.dao;

import com.cgwx.data.entity.GisSubdivisionProductInfo;
import java.util.List;

public interface GisSubdivisionProductInfoMapper {
    int insert(GisSubdivisionProductInfo record);

    List<GisSubdivisionProductInfo> selectAll();
}