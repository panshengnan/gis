package com.cgwx.dao;

import com.cgwx.data.entity.GisProvinceMapInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface GisProvinceMapInfoMapper {
    int insert(GisProvinceMapInfo record);

    List<GisProvinceMapInfo> selectAll();

    @Select({"SELECT area_name,\n" +
            "        st_asgeojson(image_geo) as geo,\n" +
            "        layer_name,download_url,location\n" +
            "FROM   gis_province_map_info\n" +
            " WHERE   1 = 1"
    })
    @Results({@Result(
            column = "area_name",
            property = "areaName"
    ), @Result(
            column = "layer_name",
            property = "layerName"
    ), @Result(
            column = "download_url",
            property = "downloadUrl"
    ), @Result(
            column = "geo",
            property = "imageGeo"
    ), @Result(
            column = "location",
            property = "location"
    )})
    List<GisProvinceMapInfo> getAllProvinceMap();
}