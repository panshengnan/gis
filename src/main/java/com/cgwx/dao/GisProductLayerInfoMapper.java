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
    @Select({"SELECT layer_name\n  " +
            "  FROM gis_product_layer_info\n  " +
            "    WHERE product_id = #{productId} and single_id = #{singleId}"})
    String getThemeticProductLayerName(@Param("productId")String productId,
                                       @Param("singleId")String singleId);

    @Select({"SELECT layer_name\n  " +
            "  FROM gis_product_layer_info\n  " +
            "    WHERE product_id = #{productId}"})
    String getAdvanceProductLayerName(@Param("productId")String productId);

    @Select({"<script>"+
            "SELECT legend\n  " +
            "  FROM gis_product_layer_info\n  " +
            "    WHERE product_id = #{productId} "+
            "<if test='null!= singleId &amp; !\"\".equals(singleId)'>" +
            " and single_id = #{singleId} "
            + "</if>"+
            "</script>"})
    String getLayerLegend(@Param("productId")String productId,@Param("singleId")String singleId);

    @Select({"<script>"+
            "SELECT is_shp\n  " +
            "  FROM gis_product_layer_info\n  " +
            "    WHERE product_id = #{productId} "+
            "<if test='null!= singleId &amp; !\"\".equals(singleId)'>" +
            " and single_id = #{singleId} "
            + "</if>"+
            "</script>"})
    String getIsShp(@Param("productId")String productId,@Param("singleId")String singleId);

    @Select({"<script>"+
            "SELECT layer_name\n  " +
            "  FROM gis_product_layer_info\n  " +
            "    WHERE product_id = #{productId} "+
            "<if test='null!= singleId &amp; !\"\".equals(singleId)'>" +
            " and single_id = #{singleId} "
            + "</if>"+
            "</script>"})
    String getLayerName(@Param("productId")String productId,@Param("singleId")String singleId);
}

