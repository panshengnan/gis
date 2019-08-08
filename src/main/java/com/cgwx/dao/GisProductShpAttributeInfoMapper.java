package com.cgwx.dao;

import com.cgwx.data.dto.SldPathAndNameDto;
import com.cgwx.data.entity.GisProductShpAttributeInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GisProductShpAttributeInfoMapper {
    int insert(GisProductShpAttributeInfo record);

    List<GisProductShpAttributeInfo> selectAll();



    @Select({"<script>" +
            "SELECT attribute_name\n  " +
            "  FROM gis_product_shp_attribute_info\n  " +
            "    WHERE product_id = #{productId} " +
            "<if test='null!= singleId &amp; !\"\".equals(singleId)'>" +
            " and single_id = #{singleId} "
            + "</if>"+
            "</script>"
    })
    List<String> getAttributeList(@Param("productId")String productId, @Param("singleId")String singleId);

    @Select({"<script>" +
            "SELECT id\n  " +
            "  FROM gis_product_shp_attribute_info\n  " +
            "    WHERE product_id = #{productId} and attribute_name = #{attributeName} " +
            "<if test='null!= singleId &amp; !\"\".equals(singleId)'>" +
            " and single_id = #{singleId} "
            + "</if>"+
            "</script>"
    })
    int getId(@Param("productId")String productId, @Param("singleId")String singleId, @Param("attributeName")String attributeName);


    @Select({"<script>" +
            "SELECT value_count\n  " +
            "  FROM gis_product_shp_attribute_info\n  " +
            "    WHERE product_id = #{productId} and attribute_name = #{attributeName} " +
            "<if test='null!= singleId &amp; !\"\".equals(singleId)'>" +
            " and single_id = #{singleId} "
            + "</if>"+
            "</script>"
    })
    int getValueCount(@Param("productId")String productId, @Param("singleId")String singleId, @Param("attributeName")String attributeName);

    @Select({"<script>" +
            "SELECT flag\n  " +
            "  FROM gis_product_shp_attribute_info\n  " +
            "    WHERE product_id = #{productId} and attribute_name = #{attributeName} " +
            "<if test='null!= singleId &amp; !\"\".equals(singleId)'>" +
            " and single_id = #{singleId} "
            + "</if>"+
            "</script>"
    })
    String getAttributeFlag(@Param("productId")String productId, @Param("singleId")String singleId, @Param("attributeName")String attributeName);

    @Select({"<script>" +
            "SELECT other_name\n  " +
            "  FROM gis_product_shp_attribute_info\n  " +
            "    WHERE product_id = #{productId} and attribute_name = #{attributeName} " +
            "<if test='null!= singleId &amp; !\"\".equals(singleId)'>" +
            " and single_id = #{singleId} "
            + "</if>"+
            "</script>"
    })
    String getAttributeOtherName(@Param("productId")String productId, @Param("singleId")String singleId, @Param("attributeName")String attributeName);

    @Select({"<script>" +
            "update gis_product_shp_attribute_info set value_count = #{valueNum} \n  " +
            "    WHERE product_id = #{productId} and attribute_name = #{attributeName} " +
            "<if test='null!= singleId &amp; !\"\".equals(singleId)'>" +
            " and single_id = #{singleId} "
            + "</if>"+
            "</script>"
    })
    void updateValueCount(@Param("productId")String productId, @Param("singleId")String singleId, @Param("attributeName")String attributeName,@Param("valueNum")int valueNum);
}