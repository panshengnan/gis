package com.cgwx.dao;

import com.cgwx.data.entity.GisThemeticProductIndustryInfo;
import java.util.List;

public interface GisThemeticProductIndustryInfoMapper {
    int insert(GisThemeticProductIndustryInfo record);

    List<GisThemeticProductIndustryInfo> selectAll();
}