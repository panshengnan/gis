package com.cgwx.dao;

import com.cgwx.data.dto.AttributeInfoDto;
import com.cgwx.data.entity.GisProductShpAttributeDetailInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GisProductShpAttributeDetailInfoMapper {
    int insert(GisProductShpAttributeDetailInfo record);

    List<GisProductShpAttributeDetailInfo> selectAll();


    @Select({"<script>" +
            "SELECT attribute_value\n  " +
            "  FROM gis_product_shp_attribute_detail_info\n  " +
            "    WHERE attribute_id = #{attributeId} " +
//            "<if test='null!= singleId &amp; !\"\".equals(singleId)'>" +
//            " and single_id = #{singleId} "
//            + "</if>"+
            "</script>"
    })
    List<String> getAttributeValues(@Param("attributeId")int attributeId);

    @Select({"<script>" +
            "SELECT other_value\n  " +
            "  FROM gis_product_shp_attribute_detail_info\n  " +
            "    WHERE attribute_id = #{attributeId} and attribute_value = #{attributeValue} " +
//            "<if test='null!= singleId &amp; !\"\".equals(singleId)'>" +
//            " and single_id = #{singleId} "
//            + "</if>"+
            "</script>"
    })
    String  getAttributeOtherValue(@Param("attributeId")int attributeId,@Param("attributeValue") String attributeValue);

    @Select({"<script>" +
            "SELECT  count( DISTINCT attribute_value) from gis_product_shp_attribute_detail_info WHERE attribute_id = #{attributeId} "+
            "</script>"
    })
    int getAttributeValueCount(@Param("attributeId")int attributeId);

    @Select({"<script>" +
            "SELECT  attribute_id from gis_product_shp_attribute_detail_info WHERE data_id = #{dataId} order by gmt_modified desc limit 1"+
            "</script>"
    })
    int getAttributeNameId(@Param("dataId")int dataId);


    @Select({"<script>" +
            "SELECT  * from gis_product_shp_attribute_detail_info WHERE data_id = #{dataId} and attribute_id = #{attributeId}"+
            "</script>"
    })
    @Results({ @Result(
            column = "attribute_value",
            property = "attributeValue"
    ), @Result(
            column = "fill_color",
            property = "fillColor"
    ), @Result(
            column = "fill_opacity",
            property = "fillOpacity"
    ), @Result(
            column = "stroke_color",
            property = "strokeColor"
    ),@Result(
            column = "stroke_opacity",
            property = "strokeOpacity")
    })
    List<AttributeInfoDto> getAttributeInfoDtoList(@Param("dataId")int dataId,@Param("attributeId")int attributeId);
}