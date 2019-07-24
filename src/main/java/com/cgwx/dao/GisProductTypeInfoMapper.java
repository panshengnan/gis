package com.cgwx.dao;

import com.cgwx.data.entity.GisProductTypeInfo;
import java.util.List;

public interface GisProductTypeInfoMapper {
    int insert(GisProductTypeInfo record);

    List<GisProductTypeInfo> selectAll();
}