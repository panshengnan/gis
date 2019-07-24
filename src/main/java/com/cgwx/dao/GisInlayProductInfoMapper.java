package com.cgwx.dao;

import com.cgwx.data.entity.GisInlayProductInfo;
import java.util.List;

public interface GisInlayProductInfoMapper {
    int insert(GisInlayProductInfo record);

    List<GisInlayProductInfo> selectAll();
}