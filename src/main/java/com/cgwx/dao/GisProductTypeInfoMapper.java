package com.cgwx.dao;

import com.cgwx.data.entity.GisProductTypeInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GisProductTypeInfoMapper {
    int insert(GisProductTypeInfo record);

    List<GisProductTypeInfo> selectAll();

    @Select({"SELECT product_description\n" +
            " FROM gis_product_type_info\n" +
            "WHERE product_type = #{productType}"
    })
    String selectProductTypeDescriptionByProductType(@Param("productType") int productType);

    @Select({"SELECT product_type\n" +
            " FROM gis_product_type_info\n" +
            "WHERE product_description like #{%productTypeDescription%}"
    })
    int selectProductTypeByProductTypeDescription(@Param("productTypeDescription") String productTypeDescription);
}