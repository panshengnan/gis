package com.cgwx.dao;

import com.cgwx.data.entity.GisSatelliteInfo;
import java.util.List;

public interface GisSatelliteInfoMapper {
    int insert(GisSatelliteInfo record);

    List<GisSatelliteInfo> selectAll();
}