package com.cgwx.dao;

import com.cgwx.data.dto.SldPathAndNameDto;
import com.cgwx.data.entity.GisProductLayerInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface GisProductLayerInfoMapper {
    int insert(GisProductLayerInfo record);

    List<GisProductLayerInfo> selectAll();



    @Select({"<script>" +
            "SELECT sld_path,style_name\n  " +
            "  FROM gis_product_layer_info\n  " +
            "    WHERE product_id = #{productId} " +
            "<if test='null!= singleId &amp; !\"\".equals(singleId)'>" +
            " and single_id = #{singleId} "
            + "</if>"+
            "</script>"
    })
    @Results({  @Result(
            column = "sld_path",
            property = "sldPath"
    ),@Result(
            column = "style_name",
            property = "sldName"
    )
    })
    SldPathAndNameDto getSldPathAndName(@Param("productId")String productId, @Param("singleId")String singleId);
}