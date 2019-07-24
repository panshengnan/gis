package com.cgwx.dao;

import com.cgwx.data.entity.GisProductInfo;
import java.util.List;

public interface GisProductInfoMapper {
    int insert(GisProductInfo record);

    List<GisProductInfo> selectAll();
}