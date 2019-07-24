package com.cgwx.dao;

import com.cgwx.data.entity.GisOrderInfo;
import java.util.List;

public interface GisOrderInfoMapper {
    int insert(GisOrderInfo record);

    List<GisOrderInfo> selectAll();
}