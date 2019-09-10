package com.cgwx.dao;

import com.cgwx.data.entity.GisStandardProductType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface GisStandardProductTypeMapper {
    int insert(GisStandardProductType record);

    List<GisStandardProductType> selectAll();

    @Select({"SELECT type_description\n" +
            "FROM gis_standard_product_type\n" +
            "WHERE type_index = #{typeIndex}"
    })
    String selectTypeByIndex(@Param("typeIndex") int typeIndex);
}