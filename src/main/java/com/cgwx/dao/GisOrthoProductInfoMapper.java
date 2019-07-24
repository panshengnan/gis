package com.cgwx.dao;

import com.cgwx.data.entity.GisOrthoProductInfo;
import java.util.List;

public interface GisOrthoProductInfoMapper {
    int insert(GisOrthoProductInfo record);

    List<GisOrthoProductInfo> selectAll();
}