package com.cgwx.dao;

import com.cgwx.data.entity.GisSensorInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface GisSensorInfoMapper {
    int insert(GisSensorInfo record);

    List<GisSensorInfo> selectAll();
    @Select("SELECT sensor_description\n" +
            "FROM gis_sensor_info\n" +
            "WHERE 1=1 order by gmt_created asc "
    )
    List<String> selectSensorInfo();
}