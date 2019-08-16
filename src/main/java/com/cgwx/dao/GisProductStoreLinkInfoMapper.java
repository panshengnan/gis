package com.cgwx.dao;

import com.cgwx.data.entity.GisProductStoreLinkInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface GisProductStoreLinkInfoMapper {
    int insert(GisProductStoreLinkInfo record);

    List<GisProductStoreLinkInfo> selectAll();
    @Select({"SELECT product_id, single_period_product_id, file_name, store_link\n                       FROM gis_product_store_link_info\n                    WHERE product_id = #{productId} and single_period_product_id = #{singlePeriodProductId}"})
    @Results({@Result(
            column = "product_id",
            property = "productId"
    ), @Result(
            column = "single_period_product_id",
            property = "singlePeriodProductId"
    ), @Result(
            column = "file_name",
            property = "fileName"
    ), @Result(
            column = "store_link",
            property = "storeLink"
    )})
    List<GisProductStoreLinkInfo> selectProductStoreLinksByProductIdAndsingleId(@Param("productId") String var1, @Param("singlePeriodProductId") String var2);

    @Select({"SELECT product_id, single_period_product_id, file_name, store_link\n                       FROM gis_product_store_link_info\n                       WHERE product_id = #{productId}"})
    @Results({@Result(
            column = "product_id",
            property = "productId"
    ), @Result(
            column = "single_period_product_id",
            property = "singlePeriodProductId"
    ), @Result(
            column = "file_name",
            property = "fileName"
    ), @Result(
            column = "store_link",
            property = "storeLink"
    )})
    List<GisProductStoreLinkInfo> selectProductStoreLinksByProductId(@Param("productId") String var1);

    @Select({"SELECT  store_link\n                       FROM gis_product_store_link_info\n               WHERE product_id = #{productId} and file_name like '%.pdf'and single_period_product_id=''"})
    String selectProductAnalysisReporturl(@Param("productId") String var1);

    @Select({"SELECT  store_link\n                       FROM gis_product_store_link_info\n               WHERE product_id = #{productId} and file_name like '%.zip' and single_period_product_id=''"})
    String selectProductAllfileDownloadurl(@Param("productId") String var1);

    @Select({"SELECT  store_link\n                       FROM gis_product_store_link_info\n               WHERE product_id = #{productId} and (file_name like '%.doc' or file_name like '%.docx' and single_period_product_id='')"})
    String selectProductDocAnalysisReporturl(@Param("productId") String var1);

    @Select({"SELECT  store_link\n                       FROM gis_product_store_link_info\n               WHERE product_id = #{productId} and file_name like '%.jpg'"})
    List<String> selectProductthumbnailUrlurl(@Param("productId") String var1);

    @Select({"SELECT  store_link\n                       FROM gis_product_store_link_info\n               WHERE product_id = #{productId} and single_period_product_id = #{singlePeriodProductId} and file_name like '%.jpg'"})
    List<String> selectProductthumbnailUrl(@Param("productId") String var1,@Param("singlePeriodProductId") String var2);

    @Select({"SELECT  store_link\n                       FROM gis_product_store_link_info\n               WHERE product_id = #{productId} and single_period_product_id = #{singlePeriodProductId} and file_name like '%legend%'"})
    List<String> selectProductLegendUrl(@Param("productId") String var1,@Param("singlePeriodProductId") String var2);
}