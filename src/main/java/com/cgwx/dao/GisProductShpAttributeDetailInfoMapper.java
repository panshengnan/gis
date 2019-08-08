package com.cgwx.dao;

import com.cgwx.data.entity.GisProductShpAttributeDetailInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
}