package com.cgwx.dao;

import com.cgwx.data.entity.GisSatelliteInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface GisSatelliteInfoMapper {
    int insert(GisSatelliteInfo record);

    List<GisSatelliteInfo> selectAll();
    @Select("SELECT satellite_description\n" +
            "FROM pdm_satellite_info\n" +
            "WHERE 1=1 order by gmt_created asc "
    )
    List<String> selectSatelliteInfo();
}