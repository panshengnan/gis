package com.cgwx.dao;

import com.cgwx.data.dto.Industry;
import com.cgwx.data.entity.GisThemeticProductDetailIndustryInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface GisThemeticProductDetailIndustryInfoMapper {
    int insert(GisThemeticProductDetailIndustryInfo record);

    List<GisThemeticProductDetailIndustryInfo> selectAll();
    @Select("<script>"
            +"SELECT product_id\n" +
            "            FROM gis_themetic_product_detail_industry_info\n"+
            "            WHERE 1=1 \n"+
            "<if test='level1!=10000'>"
            + "and industry_level1=#{level1}"
            +"</if>"
            +"<if test='level2!=10000'>"
            + "and industry_level2=#{level2}"
            +"</if>"
            +"</script>")

    List<String> selectThemeticidByIndustry(@Param("level1")int level1,
                                            @Param("level2")int level2);


    @Select(            "SELECT industry_level1,industry_level2\n" +
            "            FROM gis_themetic_product_detail_industry_info\n"+
            "            WHERE product_id=#{productId} \n"            )
    @Results({@Result(
            column = "industry_level1",
            property = "level1"

    ), @Result(
            column = "industry_level2",
            property = "level2")
    })

    List<Industry> selectIndustryByProductid(@Param("productId")String productId);


    @Select({"SELECT product_id\n" +
            "            FROM gis_themetic_product_detail_industry_info\n"+
            "            WHERE ${where}"})

    List<String> selectThemeticidByIndustrylist(@Param("where")String where);
}