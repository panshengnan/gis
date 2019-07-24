package com.cgwx.dao;

import com.cgwx.data.entity.GisAdvancedProductShpInfo;
import java.util.List;

public interface GisAdvancedProductShpInfoMapper {
    int insert(GisAdvancedProductShpInfo record);

    List<GisAdvancedProductShpInfo> selectAll();
}