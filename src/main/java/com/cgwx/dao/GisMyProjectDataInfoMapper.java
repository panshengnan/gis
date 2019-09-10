package com.cgwx.dao;

import com.cgwx.data.dto.ProjectDataDto;
import com.cgwx.data.dto.ProjectDto;
import com.cgwx.data.entity.GisMyProjectDataInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GisMyProjectDataInfoMapper {
    int insert(GisMyProjectDataInfo record);

    List<GisMyProjectDataInfo> selectAll();

    @Select({"<script>" +
            "SELECT *,st_asgeojson(geo) as image_geo FROM gis_my_project_data_info WHERE project_id = #{projectId} and status = \'SAVED\' " +
            "order by gmt_modified desc" +
            "</script>"
    })
    @Results({@Result(
            column = "layer_name",
            property = "layerName"

    ), @Result(
            column = "id",
            property = "dataId"
    ), @Result(
            column = "image_geo",
            property = "geo"
    ), @Result(
            column = "is_edit",
            property = "isEdit"
    ), @Result(
            column = "data_name",
            property = "dataName"
    ),@Result(
            column = "tif_opacity",
            property = "tifOpacity"
    ),@Result(
            column = "product_id",
            property = "productId"
    ),@Result(
            column = "single_id",
            property = "singleId")
    })
    List<ProjectDataDto> getProjectDataDtoList(@Param("projectId") int projectId);


}