package com.cgwx.dao;

import com.cgwx.data.entity.GisDomesticAreaInfo;
import java.util.List;

public interface GisDomesticAreaInfoMapper {
    int insert(GisDomesticAreaInfo record);

    List<GisDomesticAreaInfo> selectAll();
}