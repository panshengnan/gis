package com.cgwx.dao;

import com.cgwx.data.entity.GisCartInfo;
import java.util.List;

public interface GisCartInfoMapper {
    int insert(GisCartInfo record);

    List<GisCartInfo> selectAll();
}