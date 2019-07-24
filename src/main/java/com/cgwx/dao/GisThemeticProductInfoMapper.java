package com.cgwx.dao;

import com.cgwx.data.entity.GisThemeticProductInfo;
import java.util.List;

public interface GisThemeticProductInfoMapper {
    int insert(GisThemeticProductInfo record);

    List<GisThemeticProductInfo> selectAll();
}