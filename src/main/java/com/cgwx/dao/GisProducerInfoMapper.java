package com.cgwx.dao;

import com.cgwx.data.entity.GisProducerInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface GisProducerInfoMapper {
    int insert(GisProducerInfo record);

    List<GisProducerInfo> selectAll();

    @Select("SELECT count(producer)\n" +
            "FROM gis_producer_info\n" +
            "WHERE producer like  '${producerName}' "
    )
    int selectCountByProducerName(@Param("producerName") String producerName);


    @Select("SELECT  producer\n" +
            "FROM gis_producer_info\n" +
            "WHERE producer like  '%${producer}%' and producer <> '' order by producer collate \"C\" "
    )
//    @Results({@Result(
//            column = "product_id",
//            property = "productId"
//    ), @Result(
//            column = "product_description",
//            property = "productDescription"
//    ), @Result(
//            column = "product_id",
//            property = "productId"
//    ), @Result(
//            column = "industry",
//            property = "industry"
//    ),@Result(
//            column = "product_type",
//            property = "productType"
//    )})
    List<String> selectProducerList(@Param("producer") String producer);
}