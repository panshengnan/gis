package com.cgwx.dao;

import com.cgwx.data.entity.GisSensorInfo;
import java.util.List;

public interface GisSensorInfoMapper {
    int insert(GisSensorInfo record);

    List<GisSensorInfo> selectAll();
}