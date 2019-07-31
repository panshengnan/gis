package com.cgwx.dao;

import com.cgwx.data.dto.CountryInfo;
import com.cgwx.data.entity.GisCountryAreaInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface GisCountryAreaInfoMapper {
    int insert(GisCountryAreaInfo record);

    List<GisCountryAreaInfo> selectAll();
    @Select(" SELECT  nation_chinese_name,ST_AsGeoJSON(geo)as area_geojson FROM pdm_country_area_info")
    @Results(value = {
            @Result(column="nation_chinese_name", property="countryName"),
            @Result(column="area_geojson" ,property="imageGeo")
    })
    List<CountryInfo>selectAllcountry();
}