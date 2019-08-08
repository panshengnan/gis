package com.cgwx.dao;

import com.cgwx.data.dto.AreaInfoDto;
import com.cgwx.data.entity.GisDomesticAreaInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface GisDomesticAreaInfoMapper {
    int insert(GisDomesticAreaInfo record);

    List<GisDomesticAreaInfo> selectAll();
    @Select(" SELECT  area_id,area_name,ST_AsGeoJSON(area_geo)as area_geojson FROM gis_domestic_area_info WHERE parent_area_id=#{parentId} order by area_name")
    @Results(value = {
            @Result( column="area_id",property="areaId" ),
            @Result(column="area_name", property="areaName"),
            @Result(column="area_geojson" ,property="areaGeojson")
    })
    List<AreaInfoDto>selectAllChild(Integer parentId);
}