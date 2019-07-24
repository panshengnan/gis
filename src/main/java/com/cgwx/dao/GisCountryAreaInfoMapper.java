package com.cgwx.dao;

import com.cgwx.data.entity.GisCountryAreaInfo;
import java.util.List;

public interface GisCountryAreaInfoMapper {
    int insert(GisCountryAreaInfo record);

    List<GisCountryAreaInfo> selectAll();
}