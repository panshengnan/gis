package com.cgwx.dao;

import com.cgwx.data.entity.GisOrderProductInfo;
import java.util.List;

public interface GisOrderProductInfoMapper {
    int insert(GisOrderProductInfo record);

    List<GisOrderProductInfo> selectAll();
}