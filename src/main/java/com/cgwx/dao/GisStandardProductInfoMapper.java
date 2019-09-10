package com.cgwx.dao;

import com.cgwx.data.dto.StandardProductDetail;
import com.cgwx.data.entity.GisStandardProductInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface GisStandardProductInfoMapper {
    int insert(GisStandardProductInfo record);

    List<GisStandardProductInfo> selectAll();
    @Select({"SELECT product_id,\n" +
            "        st_asgeojson(image_geo) as geo,\n" +
            "        satellite_id,sensor,imaging_time_str, \n" +
            "       image_gsd,cloud_percent,\n" +
            "        product_quality, band_amount, band_string, thumbnail,product_type,product_band,\n" +
            "        roll_satellite_angle,solar_elevation,center_longitude,center_latitude \n"+
            "FROM   gis_standard_product_info\n" +
            " WHERE   product_id = #{productId}"
    })
    @Results({@Result(
            column = "product_id",
            property = "productId"
    ), @Result(
            column = "satellite_id",
            property = "satelliteId"
    ), @Result(
            column = "sensor",
            property = "sensor"
    ), @Result(
            column = "geo",
            property = "imageGeo"

    ), @Result(
            column = "imaging_time_str",
            property = "imagingTimeStr"
    ),@Result(
            column = "image_gsd",
            property = "imageGsd"
    ),@Result(
            column = "cloud_percent",
            property = "cloudPercent"
    ),@Result(
            column = "product_quality",
            property = "productQuality"
    ),@Result(
            column = "band_amount",
            property = "bandAmount"
    ),@Result(
            column = "band_string",
            property = "bandString"
    ),@Result(
            column = "thumbnail",
            property = "thumbnail"
    ),@Result(
            column = "product_type",
            property = "productType"
    ),@Result(
            column = "product_band",
            property = "productBand"
    ),@Result(
            column = "roll_satellite_angle",
            property = "rollSatelliteAngle"
    ),@Result(
            column = "solar_elevation",
            property = "solarElevation"
    ),@Result(
            column = "center_longitude",
            property = "centerLongitude"
    ),@Result(
            column = "center_latitude",
            property = "centerLatitude"
    )
    })
        //查询请求详细信息调用的数据库语句
    StandardProductDetail selectStandardProductDetailByProductId(@Param("productId") String productId);

    @Select({"update gis_standard_product_info\n" +
            "  set image_geo = st_geomfromgeojson(#{geoJson})\n"
            + " where product_id = #{productId} "
    })
    void updateStandardproductImgGeo(@Param("productId") String productId,@Param("geoJson") String geoJson);

}