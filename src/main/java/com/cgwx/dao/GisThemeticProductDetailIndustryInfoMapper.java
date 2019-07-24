package com.cgwx.dao;

import com.cgwx.data.entity.GisThemeticProductDetailIndustryInfo;
import java.util.List;

public interface GisThemeticProductDetailIndustryInfoMapper {
    int insert(GisThemeticProductDetailIndustryInfo record);

    List<GisThemeticProductDetailIndustryInfo> selectAll();
}