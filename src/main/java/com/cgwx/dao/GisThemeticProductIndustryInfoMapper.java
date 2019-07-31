package com.cgwx.dao;

import com.cgwx.data.entity.GisThemeticProductIndustryInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface GisThemeticProductIndustryInfoMapper {
    int insert(GisThemeticProductIndustryInfo record);

    List<GisThemeticProductIndustryInfo> selectAll();
}