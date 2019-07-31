package com.cgwx.dao;

import com.cgwx.data.entity.GisProductLayerInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface GisProductLayerInfoMapper {
    int insert(GisProductLayerInfo record);

    List<GisProductLayerInfo> selectAll();
    @Select({"SELECT layer_name\n  " +
            "  FROM pdm_product_layer_info\n  " +
            "    WHERE product_id = #{productId} and single_id = #{singleId}"})
    String getThemeticProductLayerName(@Param("productId")String productId,
                                       @Param("singleId")String singleId);

    @Select({"SELECT layer_name\n  " +
            "  FROM pdm_product_layer_info\n  " +
            "    WHERE product_id = #{productId}"})
    String getAdvanceProductLayerName(@Param("productId")String productId);
}