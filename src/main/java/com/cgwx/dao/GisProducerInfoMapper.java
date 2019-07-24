package com.cgwx.dao;

import com.cgwx.data.entity.GisProducerInfo;
import java.util.List;

public interface GisProducerInfoMapper {
    int insert(GisProducerInfo record);

    List<GisProducerInfo> selectAll();
}